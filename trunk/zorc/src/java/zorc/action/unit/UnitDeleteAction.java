package zorc.action.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.ServiceFactory;
import zorc.service.UnitService;

/**
 * Действие п оудалению единицы измерения.
 *
 * @author Genocide
 */
public class UnitDeleteAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(UnitDeleteAction.class);
    
    @Override
    public String getName() {
        return Constants.UNIT_DELETE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UnitService unitService = ServiceFactory.getService(UnitService.class);
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                unitService.delete(id);
            }
            unitService.close();
            return ActionResult.redirect(Constants.UNIT_LIST_ACTION_NAME);
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
