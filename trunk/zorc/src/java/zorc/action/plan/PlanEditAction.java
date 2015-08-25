package zorc.action.plan;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.entity.Production;
import zorc.entity.Unit;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.PlanChangeService;
import zorc.service.PlanService;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;
import zorc.service.UnitService;

/**
 * Действие по редактированию плана.
 *
 * @author Genocide
 */
public class PlanEditAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanEditAction.class);

    @Override
    public String getName() {
        return Constants.PLAN_EDIT_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Если передан id редактируемого плана - показываем именно этот план.
            if (request.getParameter("id") != null) {
                PlanService planService = ServiceFactory.getService(PlanService.class);
                ProductionService productionService = ServiceFactory.getService(ProductionService.class);
                UnitService unitService = ServiceFactory.getService(UnitService.class);
                PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
                
                int id = Integer.parseInt(request.getParameter("id"));
                Plan plan = planService.read(id);
                PlanChange change = changeService.findUnfixed();
                boolean found = false;
                for (Plan unfixedPlan : change.getPlans()) {
                    if (unfixedPlan.getId() == id) {
                        found = true;
                        break;
                    }
                }
                planService.close();
                productionService.close();
                unitService.close();
                changeService.close();
                
                if (!found) {
                    return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
                }
                
                plan.setProduct(productionService.read(plan.getProduct().getId()));
                plan.setUnit(unitService.read(plan.getUnit().getId()));
                request.setAttribute("plan", plan);

                // Запоминаем список единиц измерения.
                List<Unit> unitList = unitService.readAll();
                request.setAttribute("unitList", unitList);
            }
            // Иначе просто показываем страницу, т.к. она используется в случае создания плана.
            return ActionResult.forward(Constants.PLAN_EDIT_PAGE_PATH);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
