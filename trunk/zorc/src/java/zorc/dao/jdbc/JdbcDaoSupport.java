package zorc.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.exception.DataException;

/**
 * Вспомогательный класс для реализации DAO.
 *
 * @author Genocide
 */
public class JdbcDaoSupport {

    protected Logger log = LoggerFactory.getLogger(getClass());
    private Connection connection;

    public JdbcDaoSupport(Connection connection) {
        this.connection = connection;
    }

    /**
     * Подставить значения в подготовленный запрос.
     *
     * @param ps Объект PreparedStatement.
     * @param args Подставляемые значения.
     * @throws DataException Несоответствие подставляемых параметров их числу в
     * запросе.
     */
    private void doSetValue(PreparedStatement ps, Object[] args)
            throws DataException {
        for (int i = 0; i < args.length; i++) {
            try {
                if (args[i] instanceof String) {
                    /**
                     * По умолчанию у PreparedStatement стоит кодировка
                     * windows-1251. Чтобы работать с кириллицей, меняем ее на
                     * utf-8.
                     */
                    ps.setBytes(i + 1, ((String) args[i]).getBytes("UTF-8"));
                } else {
                    ps.setObject(i + 1, args[i]);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    /**
     * Выполнить CREATE запрос.
     *
     * @param sql Запрос, который будем выполнять.
     * @param args Объекты, которые будем подставлять в запрос.
     * @return Идентификатор добавленной записи.
     */
    public Integer queryForCreate(String sql, Object... args) throws DataException {
        Integer val = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (args != null) {
                ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                doSetValue(ps, args);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
            }
            if (rs.next()) {
                val = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
        }

        return val;
    }
    
    /**
     * Выполнить запрос, результатом которого будет число.
     *
     * @param sql Запрос, который будем выполнять.
     * @param args Объекты, которые будем подставлять в запрос.
     * @return Число, которое является результатом запроса.
     */
    public Integer queryForInteger(String sql, Object... args) throws DataException {
        Integer val = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            doSetValue(ps, args);
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                val = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
        }

        return val;
    }

    /**
     * Выполнить запрос, результатом которого является объект.
     *
     * @param <T> Тим возвращаемого объекта.
     * @param sql Сам запрос.
     * @param rowMapper Реализация интерфейса RowMapper для объекта T, которая
     * говорит как формируется объект на основании ResultSet'а.
     * @param args Объекты, подставляемые в запрос.
     * @return Объект типа T.
     */
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper,
            Object... args) throws DataException {
        T t = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            doSetValue(ps, args);
            ps.execute();
            rs = ps.getResultSet();
            if (rs.next()) {
                t = rowMapper.mapRow(rs);
            }
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
        }

        return t;
    }

    /**
     * Выполнить запрос, результатом которого является список объектов.
     *
     * @param <T> Тип возвращаемых объектов.
     * @param sql Текст запроса.
     * @param rowMapper Реализация интерфейса RowMapper для объекта T, которая
     * говорит как формируется объект на основании ResultSet'а.
     * @param args Объекты, подставляемые в запрос.
     * @return Список объектов типа T.
     */
    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper,
            Object... args) throws DataException {
        List<T> list = new ArrayList<T>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            doSetValue(ps, args);
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
        }

        return list;
    }

    /**
     * Выполнить запрос на обновление строк в таблице БД. Чаще используется при
     * UPDATE и DELETE запросах.
     *
     * @param sql Текст запроса.
     * @param args Объекты, подставляемые в запрос.
     * @return Количество измененных строк.
     */
    public int update(String sql, Object... args) throws DataException {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            doSetValue(ps, args);
            int rows = ps.executeUpdate();
            return rows;
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                log.debug(null, e);
            }
        }
    }
}
