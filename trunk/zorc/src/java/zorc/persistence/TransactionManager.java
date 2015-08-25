package zorc.persistence;

import java.sql.SQLException;
import zorc.dao.GenericDAO;
import zorc.entity.Entity;
import zorc.exception.DataException;


/**
 * @author Genocide
 * 
 * Менеджер ДАО.
 */
public abstract class TransactionManager {
	
	abstract public <Type extends GenericDAO<? extends Entity>> Type createDao(Class<Type> key);

	abstract public void transactionCommit() throws DataException;

	abstract public void transactionRollback() throws DataException;

	abstract public void close() throws SQLException;
}
