package zorc.action.plan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.PlanService;
import zorc.service.ServiceFactory;

/**
 * Действие по удалению плана.
 * @author Genocide
 */
public class PlanDeleteAction implements Action{

    private static final Logger logger = LoggerFactory.getLogger(PlanDeleteAction.class);
    
    @Override
    public String getName() {
        return Constants.PLAN_DELETE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanService planService = ServiceFactory.getService(PlanService.class);
            
            // Если передали id плана, то удаляем его
            if(request.getParameter("id")!=null){
                int id = Integer.parseInt(request.getParameter("id"));
                planService.delete(id);
            }
            
            planService.close();
            return ActionResult.redirect(Constants.PLAN_LIST_ACTION_NAME);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
    
}
