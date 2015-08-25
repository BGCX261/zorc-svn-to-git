package zorc.action.plan;

import java.util.ArrayList;
import java.util.List;
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
import zorc.service.ServiceFactory;

/**
 * @author rsd 
 * 
 * Действие по просмотру плана.
 */
public class PlanListAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PlanListAction.class);
    
    @Override
    public String getName() {
        return Constants.PLAN_LIST_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanService planService = ServiceFactory.getService(PlanService.class);
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            
            Integer prevId = null, nextId = null;
            
            PlanChange change;
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                change = changeService.read(id);
                nextId = changeService.getNextId(change);
            } else {
                change = changeService.findUnfixed();
            }
            
            List<Plan> previousPlans;
            List<Plan> removed = new ArrayList<>();
            List<Plan> added = new ArrayList<>();
            List<Plan> modified = new ArrayList<>();
            List<Plan> notChanged = new ArrayList<>();
            
            // Если не создано ни одного плана
            if (change == null) {
                change = new PlanChange();
                change.setDate(null);
                changeService.save(change);
                change = changeService.findUnfixed();
            } else {
                // Получение предыдущего плана для сравнения
                PlanChange previous = changeService.getPrevious(change);
                if (previous != null) {
                    prevId = previous.getId();
                }
                // Если этот план - не текущий, то не отображать различия
                if (nextId != null) {
                    notChanged = change.getPlans();
                } else {
                    // Если предыдущего плана нет, то все записи - добавлены
                    if (previous == null) {
                        added = change.getPlans();
                    } else {
                        previousPlans = previous.getPlans();
                        // Если в новом плане есть запись с такой же продукцией
                        for (Plan plan : change.getPlans()) {
                            boolean isFound = false;
                            for (Plan previousPlan : previousPlans) {
                                if (plan.getProduct().equals(previousPlan.getProduct())) {
                                    if (plan.equals(previousPlan)) {
                                        // Запись не изменена
                                        notChanged.add(plan);
                                    } else {
                                        // Запись изменена
                                        modified.add(plan);
                                    }
                                    // Убираем запись из рассмотрения
                                    previousPlans.remove(previousPlan);
                                    isFound = true;
                                    break;
                                }
                            }
                            // Если запись не найдена, значит, она была добавлена
                            if (!isFound) {
                                added.add(plan);
                            }
                        }
                        // Записи, не найденные в предыдущем плане - удалены
                        removed = previousPlans;
                    }
                }                
            }
            
            //
            if (request.getParameter("typeSearch") != null && request.getParameter("searchText") != null && !request.getParameter("searchText").isEmpty()) {
                boolean byCode = ((String) request.getParameter("typeSearch")).equalsIgnoreCase("by_date");
                String searchText = request.getParameter("searchText");
                if ("by_date".equals(request.getParameter("typeSearch"))) {
                    searchText = DateStringFormat.logicalToDataBase(request.getParameter("searchText"));
                }
                //planList = byCode ? planService.findByDate(Date.valueOf(searchText)) : planService.findByName(searchText);
                request.setAttribute("typeSearch", byCode ? "by_date" : "by_name");
                request.setAttribute("searchText", request.getParameter("searchText"));
            }
            
            // Задаём атрибуты для использования на JSP-странице
            request.setAttribute("change", change);
            request.setAttribute("addedPlans", added);
            request.setAttribute("modifiedPlans", modified);
            request.setAttribute("notChangedPlans", notChanged);
            request.setAttribute("removedPlans", removed);
            // Предыдущий и следующий идентификаторы планов (для навигации)
            request.setAttribute("prevId", prevId);
            request.setAttribute("nextId", nextId);
            // Закрываем сервисы
            planService.close();
            changeService.close();
            // Возвращаем результат – загрузка JSP
            return ActionResult.forward(Constants.PLAN_LIST_PAGE_PATH);
        } catch (DataException ex) {
            // В случае исключения – переадресует на страницу с ошибкой
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
