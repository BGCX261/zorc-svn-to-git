package zorc.action.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.Unit;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.service.ServiceFactory;
import zorc.service.UnitService;

/**
 * Действие по редактированию единицы измерения.
 *
 * @author Genocide
 */
public class UnitEditAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(UnitEditAction.class);

    @Override
    public String getName() {
        return Constants.UNIT_EDIT_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UnitService unitService = ServiceFactory.getService(UnitService.class);
            boolean readed = false;
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                Unit unit = (id == 0) ? new Unit() : unitService.read(id);
                request.setAttribute("unit", unit);
                readed = true;
            }
            unitService.close();
            if (readed) {
                return ActionResult.forward(Constants.UNIT_EDIT_PAGE_PATH);
            } else {
                return ActionResult.redirect(Constants.UNIT_LIST_ACTION_NAME);
            }
        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
