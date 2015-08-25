package zorc.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.persistence.Constants;

/**
 * Действие по умолчанию.
 * @author Genocide
 */
public class DefaultAction implements Action {

    @Override
    public String getName() {
        return Constants.DEFAULT_AVCION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
    }
}
