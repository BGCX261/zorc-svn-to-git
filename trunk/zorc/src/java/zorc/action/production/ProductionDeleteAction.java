package zorc.action.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;

/**
 * Действие по удалению продукции.
 *
 * @author Genocide
 */
public class ProductionDeleteAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductionDeleteAction.class);

    @Override
    public String getName() {
        return Constants.PRODUCTION_DELETE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProductionService productionService = ServiceFactory.getService(ProductionService.class);

            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                productionService.delete(id);
            }

            productionService.close();
            return ActionResult.redirect(Constants.PRODUCTION_LIST_ACTION_NAME);

        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
