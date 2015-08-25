package zorc.service.impl;

import java.util.List;
import zorc.persistence.TransactionManager;
import zorc.dao.UnitDAO;
import zorc.entity.Unit;
import zorc.exception.DataException;
import zorc.service.UnitService;

/**
 * @author Genocide
 * 
 * Реализания сервиса для единиц измерений.
 */
public class UnitServiceImpl extends Support implements UnitService {

    private UnitDAO dao = getManager().createDao(UnitDAO.class);

    public UnitServiceImpl(TransactionManager manager) {
        super(manager);
    }

    @Override
    public void save(Unit entity) throws DataException {
        if (entity.getId() == null) {
            this.dao.create(entity);
        } else {
            this.dao.update(entity);
        }
    }

    @Override
    public Unit read(Integer id) throws DataException {
        return this.dao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataException {
        this.dao.delete(id);
    }

    @Override
    public List<Unit> readAll() throws DataException {
        return this.dao.readAll();
    }

    @Override
    public Unit findBySymbol(String symbol) throws DataException {
        return this.dao.findBySymbol(symbol);
    }
}
