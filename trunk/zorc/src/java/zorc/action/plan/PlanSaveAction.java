package zorc.action.plan;

import java.sql.Date;
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
import zorc.persistence.DateStringFormat;
import zorc.service.PlanChangeService;
import zorc.service.PlanService;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;
import zorc.service.UnitService;

/**
 * Действие по сохранению плана.
 * @author Genocide
 */
public class PlanSaveAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanSaveAction.class);

    @Override
    public String getName() {
        return Constants.PLAN_SAVE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanService planService = ServiceFactory.getService(PlanService.class);
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            ProductionService productionService = ServiceFactory.getService(ProductionService.class);
            UnitService unitService = ServiceFactory.getService(UnitService.class);
            
            int id = Integer.parseInt(request.getParameter("id"));
            Plan plan;
            
            // Запоминаем данные с формы.
            Integer position = Integer.parseInt(request.getParameter("position"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            Double price = Double.parseDouble(request.getParameter("price"));
            Double amount = Double.parseDouble(request.getParameter("amount"));
            Date begin = Date.valueOf(DateStringFormat.logicalToDataBase(request.getParameter("begin")));
            Date end = Date.valueOf(DateStringFormat.logicalToDataBase(request.getParameter("end")));
            int unitId = Integer.parseInt(request.getParameter("unitId"));
            String comment = request.getParameter("comment");
            String name = request.getParameter("name");
            
            PlanChange current = changeService.findUnfixed();
            
            // Проверяем, создаем или редактируем план.
            if (id == 0) {
               plan = new Plan(); 
            } else {
               plan = planService.read(id);
               if (!plan.getChange().getId().equals(current.getId())) {
                   //Plan Fixed!
                   return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
               }
            }
            
            plan.setPosition(position);
            plan.setProduct(productionService.read(productId));
            plan.setPrice(price);
            plan.setAmount(amount);
            plan.setBegin(begin);
            plan.setEnd(end);
            plan.setUnit(unitService.read(unitId));
            plan.setComment(comment);
            plan.setName(name);
            plan.setChange(current);
            
            // Предполагается, что в форме редактирования мы видим имя продукции и его и передаем в качестве параметра.
            // Может потом переделать на id продукта, и выполнять поиск тоже по id, а на форме выводить имя продукта.
            // Конечно!
            
            planService.save(plan);
            
            unitService.close();
            productionService.close();
            changeService.close();
            planService.close();
            
            return ActionResult.redirect(Constants.PLAN_LIST_ACTION_NAME);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        } 
    }
}
