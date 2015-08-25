package zorc.dao;

import java.sql.Date;
import java.util.List;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Интерфейс ДАО для плана закупок.
 */
public interface PlanDAO extends GenericDAO<Plan> {

    List<Plan> findByName(String name) throws DataException;

    List<Plan> findByDate(Date date) throws DataException;

    Integer getNextPosition(PlanChange change) throws DataException;

    public List<Plan> findByChange(PlanChange change) throws DataException;
}
