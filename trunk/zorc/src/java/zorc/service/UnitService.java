package zorc.service;

import zorc.entity.Unit;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Сервис для единиц измерений.
 */
public interface UnitService extends GenericService<Unit> {

    Unit findBySymbol(String symbol) throws DataException;
}
