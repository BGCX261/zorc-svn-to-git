package zorc.action.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.persistence.Constants;

/**
 * Действие по созданию единицы измерения.
 *
 * @author Genocide
 */
public class UnitCreateAction implements Action {

    @Override
    public String getName() {
        return Constants.UNIT_CREATE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return ActionResult.forward(Constants.UNIT_EDIT_PAGE_PATH);
    }
}
