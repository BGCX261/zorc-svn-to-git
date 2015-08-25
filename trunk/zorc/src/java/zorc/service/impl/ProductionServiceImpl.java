package zorc.service.impl;

import java.util.List;
import zorc.dao.ProductionDAO;
import zorc.entity.Production;
import zorc.exception.DataException;
import zorc.persistence.TransactionManager;
import zorc.service.ProductionService;

/**
 * @author Genocide
 *
 * Реализация сервиса для продукции.
 */
public class ProductionServiceImpl extends Support implements ProductionService{

    private ProductionDAO dao;

    public ProductionServiceImpl(TransactionManager manager) {
        super(manager);
        this.dao = getManager().createDao(ProductionDAO.class);
    }

    @Override
    public void save(Production entity) throws DataException {
        if (entity.getId() == null) {
            this.dao.create(entity);
        } else {
            this.dao.update(entity);
        }
    }

    @Override
    public Production read(Integer id) throws DataException {
        return this.dao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataException {
        this.dao.delete(id);
    }

    @Override
    public List<Production> readAll() throws DataException {
        return this.dao.readAll();
    }

    @Override
    public List<Production> findByName(String name)throws DataException  {
        return this.dao.findByName(name, 0);
    }
    
    @Override
    public List<Production> findByName(String name, int count)throws DataException  {
        return this.dao.findByName(name, count);
    }

    @Override
    public List<Production> findByCode(String code) throws DataException {
        return this.dao.findByCode(code, 0);
    }
    
    @Override
    public List<Production> findByCode(String code, int count) throws DataException {
        return this.dao.findByCode(code, count);
    }
}
