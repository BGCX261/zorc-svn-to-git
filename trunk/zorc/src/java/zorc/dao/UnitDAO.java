package zorc.dao;

import zorc.entity.Unit;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Интерфейс ДАО для единиц измерения.
 */
public interface UnitDAO extends GenericDAO<Unit> {

    Unit findBySymbol(String symbol) throws DataException;
}
