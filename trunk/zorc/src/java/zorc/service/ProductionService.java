package zorc.service;

import java.util.List;
import zorc.entity.Production;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Сервис для продукции.
 */
public interface ProductionService extends GenericService<Production> {

    List<Production> findByName(String name) throws DataException;
    List<Production> findByName(String name, int count) throws DataException;
    List<Production> findByCode(String code) throws DataException;
    List<Production> findByCode(String code, int count) throws DataException;
}
