package zorc.service.impl;

import java.sql.Date;
import java.util.List;
import zorc.dao.PlanDAO;
import zorc.dao.ProductionDAO;
import zorc.dao.UnitDAO;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.entity.Production;
import zorc.entity.Unit;
import zorc.exception.DataException;
import zorc.persistence.TransactionManager;
import zorc.service.PlanService;

/**
 * @author Genocide Реализация сервиса для плана.
 *
 */
public class PlanServiceImpl extends Support implements PlanService {

    private final PlanDAO dao = getManager().createDao(PlanDAO.class);
    private final UnitDAO unitDAO = getManager().createDao(UnitDAO.class);
    private final ProductionDAO prodDAO = getManager().createDao(ProductionDAO.class);

    public PlanServiceImpl(TransactionManager manager) {
        super(manager);
    }

    @Override
    public void save(Plan entity) throws DataException {
        if (entity.getId() == null) {
            this.dao.create(entity);
        } else {
            this.dao.update(entity);
        }
    }

    @Override
    public Plan read(Integer id) throws DataException {
        return this.dao.read(id);
    }

    @Override
    public void delete(Integer id) throws DataException {
        this.dao.delete(id);
    }

    @Override
    public List<Plan> readAll() throws DataException {
        List<Plan> planList = this.dao.readAll();
        for (Plan plan : planList) {
            plan = fillPlan(plan);
        }
        return planList;
    }

    @Override
    public List<Plan> findByName(String name) throws DataException {
        List<Plan> planList = this.dao.findByName(name);
        for (Plan plan : planList) {
            plan = fillPlan(plan);
        }
        return planList;
    }

    @Override
    public List<Plan> findByDate(Date date) throws DataException {
        List<Plan> planList = this.dao.findByDate(date);
        for (Plan plan : planList) {
            plan = fillPlan(plan);
        }
        return planList;
    }

    @Override
    public Integer getNextPosition(PlanChange change) throws DataException {
        return this.dao.getNextPosition(change);
    }

    private Plan fillPlan(Plan plan) throws DataException {
        Production p = this.prodDAO.read(plan.getProduct().getId());
        plan.setProduct(p);
        Unit u = this.unitDAO.read(plan.getUnit().getId());
        plan.setUnit(u);
        return plan;
    }
}
