package zorc.service.impl;

import java.sql.Date;
import java.util.List;
import zorc.dao.PlanChangeDAO;
import zorc.dao.PlanDAO;
import zorc.dao.ProductionDAO;
import zorc.dao.UnitDAO;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.exception.DataException;
import zorc.persistence.TransactionManager;
import zorc.service.PlanChangeService;

/**
 * @author rsd Реализация сервиса для изменения плана.
 */
public class PlanChangeServiceImpl extends Support implements PlanChangeService {

    private final PlanChangeDAO dao = getManager().createDao(PlanChangeDAO.class);
    private final PlanDAO planDAO = getManager().createDao(PlanDAO.class);
    private final UnitDAO unitDAO = getManager().createDao(UnitDAO.class);
    private final ProductionDAO prodDAO = getManager().createDao(ProductionDAO.class);

    public PlanChangeServiceImpl(TransactionManager manager) {
        super(manager);
    }

    @Override
    public void save(PlanChange entity) throws DataException {
        if (entity.getId() == null) {
            this.dao.create(entity);
        } else {
            this.dao.update(entity);
        }
    }

    @Override
    public PlanChange read(Integer id) throws DataException {
        PlanChange change = this.dao.read(id);
        return fillPlanChange(change);
    }

    @Override
    public void delete(Integer id) throws DataException {
        this.dao.delete(id);
    }

    @Override
    public List<PlanChange> readAll() throws DataException {
        List<PlanChange> planChangeList = this.dao.readAll();
        return planChangeList;
    }

    @Override
    public PlanChange findByDate(Date date) throws DataException {
        PlanChange change = this.dao.findByDate(date);
        return fillPlanChange(change);
    }

    @Override
    public PlanChange findUnfixed() throws DataException {
        PlanChange change = this.dao.findUnfixed();
        return fillPlanChange(change);
    }

    @Override
    public PlanChange getPrevious(PlanChange entity) throws DataException {
        PlanChange change = this.dao.getPrevious(entity);
        return change != null ? fillPlanChange(change) : null;
    }

    @Override
    public Integer getNextId(PlanChange change) throws DataException {
        return this.dao.getNextId(change);
    }

    private PlanChange fillPlanChange(PlanChange change) throws DataException {
        List<Plan> planList = this.planDAO.findByChange(change);
        for (Plan p : planList) {
            p.setProduct(this.prodDAO.read(p.getProduct().getId()));
            p.setUnit(this.unitDAO.read(p.getUnit().getId()));
            p.setChange(change);
        }
        change.setPlans(planList);
        return change;
    }

    @Override
    public List<PlanChange> findFixed() throws DataException {
        List<PlanChange> changes = this.dao.findFixed();
        for (PlanChange change : changes) {
            change = fillPlanChange(change);
        }
        return changes;
    }
}
