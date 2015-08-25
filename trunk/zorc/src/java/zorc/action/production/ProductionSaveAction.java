package zorc.action.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Production;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;

/**
 * Действие по сохранению продукции.
 *
 * @author Genocide
 */
public class ProductionSaveAction implements Action {

    @Override
    public String getName() {
        return Constants.PRODUCTION_SAVE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProductionService prodService = ServiceFactory.getService(ProductionService.class);

            int id = Integer.parseInt(request.getParameter("id"));

            Production prod = (id == 0) ? new Production() : prodService.read(id);
            prod.setCode(request.getParameter("code"));
            prod.setName(request.getParameter("name"));

            prodService.save(prod);

            prodService.close();

            return ActionResult.redirect(Constants.PRODUCTION_LIST_ACTION_NAME);
        } catch (DataException ex) {
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
