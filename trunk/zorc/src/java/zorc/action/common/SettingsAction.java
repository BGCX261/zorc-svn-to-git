package zorc.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.persistence.Constants;

/**
 * Действие по переходу на страницу настроек.
 * @author Genocide
 */
public class SettingsAction implements Action {

    @Override
    public String getName() {
        return Constants.SETTINGS_VIEW_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return ActionResult.forward(Constants.SETTINGS_PAGE_PATH);
    }
}
