package zorc.action.plan;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Plan;
import zorc.entity.Unit;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.PlanChangeService;
import zorc.service.PlanService;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;
import zorc.service.UnitService;

/**
 * Действие по созданию плана.
 *
 * @author Genocide
 */
public class PlanCreateAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanCreateAction.class);

    @Override
    public String getName() {
        return Constants.PLAN_CREATE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Создаем необходимые сервисы.
            ProductionService productionService = ServiceFactory.getService(ProductionService.class);
            PlanService planService = ServiceFactory.getService(PlanService.class);
            UnitService unitService = ServiceFactory.getService(UnitService.class);
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);

            List<Unit> unitList = unitService.readAll();
            request.setAttribute("unitList", unitList);
            
            // Инициализируем план.
            Plan plan = new Plan();
            plan.setId(0);
            plan.setPosition(planService.getNextPosition(changeService.findUnfixed()));

            // Устанавливаем дату начала и конца плана в рамках текущего года.
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Minsk"));
            String start = c.get(1) + "-01-01",
                    end =  c.get(1) + "-12-31";
            plan.setBegin(Date.valueOf(start));
            plan.setEnd(Date.valueOf(end));
            
            request.setAttribute("plan", plan);

            // Закрываем сервисы.
            planService.close();
            changeService.close();
            productionService.close();
            unitService.close();
            
            // Перенаправляем на страницу создания плана.
            return ActionResult.forward(Constants.PLAN_EDIT_PAGE_PATH);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            // Выполняем действие по показу ошибки.
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
