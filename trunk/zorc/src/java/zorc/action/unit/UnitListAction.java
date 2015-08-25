package zorc.action.unit;

import java.util.List;
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
 * Действие по просмотру единиц измерения.
 *
 * @author Genocide
 */
public class UnitListAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(UnitListAction.class);

    @Override
    public String getName() {
        return Constants.UNIT_LIST_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UnitService unitService = ServiceFactory.getService(UnitService.class);
            List<Unit> unitList = unitService.readAll();
            request.setAttribute("unitList", unitList);
            unitService.close();
            return ActionResult.forward(Constants.UNIT_EDIT_PAGE_PATH);

        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
