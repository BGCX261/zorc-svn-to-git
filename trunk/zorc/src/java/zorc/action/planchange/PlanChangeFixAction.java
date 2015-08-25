package zorc.action.planchange;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.PlanChangeService;
import zorc.service.PlanService;
import zorc.service.ServiceFactory;

/**
 * Действие по созданию изменения плана.
 *
 * @author rsd
 */
public class PlanChangeFixAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanChangeFixAction.class);

    @Override
    public String getName() {
        return Constants.PLAN_CHANGE_FIX_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            PlanService planService = ServiceFactory.getService(PlanService.class);
            
            // Получаем незафиксированные изменения плана.
            PlanChange change = changeService.findUnfixed();
            
            // Устанавливаем текущую дату и сохраняем изменения(фиксируем).
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Minsk"));
            change.setDate(new Date(c.getTimeInMillis()));
            changeService.save(change);
            
            // Создаем новое изменение.
            change.setDate(null);
            change.setId(null);
            changeService.save(change);
            
            PlanChange newChange = changeService.findUnfixed();
            for (Plan plan : change.getPlans()) {
                plan.setId(null);
                plan.setChange(newChange);
                planService.save(plan);
            }
            
            changeService.close();
            planService.close();
            
            return ActionResult.redirect(Constants.PLAN_LIST_ACTION_NAME);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
