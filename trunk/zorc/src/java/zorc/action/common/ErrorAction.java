package zorc.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.persistence.Constants;

/**
 * Действие - ошибка.
 * @author Genocide
 */
public class ErrorAction implements Action {

    @Override
    public String getName() {
        return Constants.ERROR_VIEW_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return ActionResult.forward(Constants.ERROR_PAGE_PATH);
    }
}
