package zorc.service;

import java.sql.Date;
import java.util.List;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Сервис для плана закупок.
 */
public interface PlanService extends GenericService<Plan> {

    List<Plan> findByName(String name) throws DataException;

    List<Plan> findByDate(Date date) throws DataException;

    Integer getNextPosition(PlanChange change) throws DataException;
}
