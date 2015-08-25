package zorc.action.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.persistence.Constants;

/**
 * Действие по созданию продукта.
 * @author Genocide
 */
public class ProductionCreateAction implements Action{

    @Override
    public String getName() {
        return Constants.PRODUCTION_CREATE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        return ActionResult.forward(Constants.PRODUCTION_EDIT_PAGE_PATH);
    }
    
}
