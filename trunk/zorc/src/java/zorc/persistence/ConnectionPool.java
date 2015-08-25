package zorc.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.exception.DataException;

/**
 * Пул соединений.
 *
 * @author Genocide
 */
public class ConnectionPool {

    private final static Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static ConnectionPool pool = new ConnectionPool();
    private static ConcurrentLinkedQueue<Connection> connections;
    private static int poolSize;
    private static String url;
    private static String login;
    private static String password;

    private ConnectionPool() {
    }

    /**
     * Инициализация пула.
     *
     * @param dbUrl URL подключения.
     * @param dbLogin Логин доступа к БД.
     * @param dbPassword Пароль доступа к БД.
     * @param dbDriver Драйвер для подключения к БД.
     * @param cpSize Размер пула.
     * @throws DataException Ошибка.
     */
    public static void init(String dbUrl, String dbLogin, String dbPassword, String dbDriver, int cpSize) throws DataException {

        try {
            // Загружаем драйвер в память.
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            LOG.error("Can't find database driver: {0}", e.getMessage());
            throw new DataException(e.getMessage());
        }
        url = dbUrl;
        login = dbLogin;
        password = dbPassword;
        poolSize = cpSize;
        connections = new ConcurrentLinkedQueue<>();
        try {
            Connection connection;
            for (int i = 0; i < poolSize; i++) {
                connection = DriverManager.getConnection(url, login, password);
                connection.setAutoCommit(false);
                connections.add(connection);
            }
        } catch (SQLException e2) {
            LOG.error("Can't get connection to database: {0}", e2.getMessage());
            throw new DataException(e2.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return pool;
    }

    public synchronized Connection getConnection() {
        Connection c = null;
        while (c == null) {
            try {
                if (!connections.isEmpty()) {
                    c = connections.remove();
                    if (!c.isValid(0)) {
                        c = null;
                    }
                } else {
                    c = DriverManager.getConnection(url, login, password);
                    c.setAutoCommit(false);
                }
            } catch (SQLException e) {
                LOG.error("Can't get connection to database: {0}", e.getMessage());
            }
        }
        return c;
    }

    public synchronized boolean releaseConnection(Connection connection) {
        if (connections.size() == poolSize) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("Can't release connection: {0}", e.getMessage());
            }
        } else {
            connections.add(connection);
        }
        return false;
    }

    public static synchronized boolean close() {
        if (connections.size() != poolSize) {
            return false;
        }
        boolean error = false;
        for (Connection c : connections) {
            try {
                c.close();
            } catch (SQLException e) {
                LOG.error("Can't close connection: {0}", e.getMessage());
                error = true;
            }
        }
        return !error;
    }
}
