package zorc.action.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Production;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.ProductionService;
import zorc.service.ServiceFactory;

/**
 * Действие по редактированию продукции.
 * 
 * @author Genocide
 */
public class ProductionEditAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ProductionEditAction.class);
    
    @Override
    public String getName() {
        return Constants.PRODUCTION_EDIT_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProductionService prodService = ServiceFactory.getService(ProductionService.class);
            boolean readed = false;
            
            
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                Production prod = prodService.read(id);
                request.setAttribute("production", prod);
                readed = true;
            }
            prodService.close();
            if (readed) {
                return ActionResult.forward(Constants.PRODUCTION_EDIT_PAGE_PATH);
            } else {
                // Если id не передали - переходим к списку продукции. И не спрашивайте почему :)
                return ActionResult.redirect(Constants.PRODUCTION_LIST_ACTION_NAME);
            }
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
