package zorc.service;

import java.sql.Date;
import java.util.List;
import zorc.entity.PlanChange;
import zorc.exception.DataException;

/**
 * @author rsd
 *
 * Сервис для изменений плана.
 */
public interface PlanChangeService extends GenericService<PlanChange> {

    PlanChange findByDate(Date date) throws DataException;
    PlanChange findUnfixed() throws DataException;
    List<PlanChange> findFixed() throws DataException;
    PlanChange getPrevious(PlanChange entity) throws DataException;
    Integer getNextId(PlanChange change) throws DataException;
}
