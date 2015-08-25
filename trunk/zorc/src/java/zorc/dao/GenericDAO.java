package zorc.dao;

import java.util.List;
import zorc.entity.Entity;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Общий интерфейс для всех ДАО с CRUD'ом и readAll'ом.
 */
public interface GenericDAO<Type extends Entity> extends DAO{

    Integer create(Type entity) throws DataException;

    Type read(Integer id) throws DataException;

    void update(Type entity) throws DataException;

    void delete(Integer id) throws DataException;

    List<Type> readAll() throws DataException;
}
