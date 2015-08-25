package zorc.service;

import java.util.List;
import zorc.entity.Entity;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Общий интерфейс для всех сервисовю
 */
public interface GenericService<Type extends Entity> extends CloseService{

    void save(Type entity) throws DataException;

    Type read(Integer id) throws DataException;

    void delete(Integer id) throws DataException;

    List<Type> readAll() throws DataException;
    
}
