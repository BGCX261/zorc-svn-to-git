package zorc.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.dao.GenericDAO;
import zorc.dao.jdbc.PlanChangeDAOImpl;
import zorc.dao.jdbc.PlanDAOImpl;
import zorc.dao.jdbc.ProductionDAOImpl;
import zorc.dao.jdbc.UnitDAOImpl;
import zorc.entity.Entity;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Реализация ДАО менеджера.
 */
public class TransactionManagerImpl extends TransactionManager {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    private Connection connection;
    private static final Map<Class<? extends GenericDAO<?>>, Class<? extends GenericDAO<?>>> DAOS = new ConcurrentHashMap<>();

    public TransactionManagerImpl() {
        connection = zorc.persistence.ConnectionPool.getInstance().getConnection();
    }

    static {
        DAOS.put(zorc.dao.PlanDAO.class, PlanDAOImpl.class);
        DAOS.put(zorc.dao.ProductionDAO.class, ProductionDAOImpl.class);
        DAOS.put(zorc.dao.UnitDAO.class, UnitDAOImpl.class);
        DAOS.put(zorc.dao.PlanChangeDAO.class, PlanChangeDAOImpl.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends GenericDAO<? extends Entity>> Type createDao(Class<Type> key) {

        Class<? extends GenericDAO<? extends Entity>> value = DAOS.get(key);
        if (value != null) {
            try {
                GenericDAO<?> dao = value.getConstructor(Connection.class).newInstance(connection);
                return (Type) dao;
            } catch (Exception e) {
                log.error("Can't create DAO "+key+"!",e);
            }
        }
        return null;
    }

    @Override
    public void transactionCommit() throws DataException {
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("Error transaction commit!",e);
            throw new zorc.exception.DataException(e);
        }
    }

    @Override
    public void transactionRollback() throws DataException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.error("Error transaction rollback!",e);
            throw new zorc.exception.DataException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        zorc.persistence.ConnectionPool.getInstance().releaseConnection(connection);
    }
}
