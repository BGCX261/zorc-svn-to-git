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
 * Жействие по сохранению единицы измерения.
 *
 * @author Genocide
 */
public class UnitSaveAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(UnitSaveAction.class);

    @Override
    public String getName() {
        return Constants.UNIT_SAVE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UnitService unitService = ServiceFactory.getService(UnitService.class);

            int id = Integer.parseInt(request.getParameter("id"));
            Unit u = (id == 0) ? new Unit() : unitService.read(id);
            u.setName(request.getParameter("name"));
            u.setSymbol(request.getParameter("symbol"));

            unitService.save(u);
            unitService.close();

            return ActionResult.redirect(Constants.UNIT_LIST_ACTION_NAME);

        } catch (DataException ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
