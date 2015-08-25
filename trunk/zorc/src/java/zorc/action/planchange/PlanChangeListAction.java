package zorc.action.planchange;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.PlanChange;
import zorc.persistence.Constants;
import zorc.service.PlanChangeService;
import zorc.service.ServiceFactory;

/**
 * Действие по просмотру изменений.
 *
 * @author rsd
 */
public class PlanChangeListAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PlanChangeListAction.class);
    
    @Override
    public String getName() {
        return Constants.PLAN_CHANGE_LIST_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            List<PlanChange> changeList = changeService.readAll();
            request.setAttribute("changeList", changeList);
            
            changeService.close();
            
            return ActionResult.forward(Constants.PLAN_CHANGE_LIST_PAGE_PATH);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
