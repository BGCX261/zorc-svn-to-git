package zorc.dao;

import java.sql.Date;
import java.util.List;
import zorc.entity.PlanChange;
import zorc.exception.DataException;

/**
 * @author rsd
 *
 * Интерфейс ДАО для изменений плана.
 */
public interface PlanChangeDAO extends GenericDAO<PlanChange> {
    PlanChange findByDate(Date date) throws DataException;
    List<PlanChange> findFixed() throws DataException;
    PlanChange findUnfixed() throws DataException;
    PlanChange getPrevious(PlanChange entity) throws DataException;
    Integer getNextId(PlanChange change) throws DataException;
}
