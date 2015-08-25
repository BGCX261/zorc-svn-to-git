package zorc.action.production;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Production;
import zorc.persistence.Constants;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;

/**
 * Действие по просмотре продукции.
 *
 * @author Genocide
 */
public class ProductionListAction implements Action {

    @Override
    public String getName() {
        return Constants.PRODUCTION_LIST_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProductionService productionService = ServiceFactory.getService(ProductionService.class);
            List<Production> productionList;
            if (request.getParameter("typeSearch") != null && request.getParameter("searchText") != null && !request.getParameter("searchText").isEmpty()) {
                boolean byCode = request.getParameter("typeSearch").equalsIgnoreCase("by_code");
                String searchText = request.getParameter("searchText");
                productionList = byCode ? productionService.findByCode(searchText) : productionService.findByName(searchText);
                request.setAttribute("typeSearch", byCode ? "by_code" : "by_name");
                request.setAttribute("searchText", searchText);
            } else {
                productionList = productionService.readAll();
            }
            request.setAttribute("productionList", productionList);
            productionService.close();
            return ActionResult.forward(Constants.PRODUCTION_LIST_PAGE_PATH);
        } catch (Exception ex) {
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
