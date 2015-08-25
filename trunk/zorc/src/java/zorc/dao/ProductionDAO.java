package zorc.dao;

import java.util.List;
import zorc.entity.Production;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Интерфес ДАО для продукции.
 */
public interface ProductionDAO extends GenericDAO<Production> {

    List<Production> findByName(String name, int count) throws DataException;

    List<Production> findByCode(String name, int count) throws DataException;
}
