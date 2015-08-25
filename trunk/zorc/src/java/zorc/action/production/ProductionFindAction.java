package zorc.action.production;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
 * Действие по поиску продукции.
 *
 * @author rsd
 */
public class ProductionFindAction implements Action {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductionFindAction.class);
    private static final int DEFAULT_MAX_COUNT = 10;

    @Override
    public String getName() {
        return Constants.PRODUCTION_FIND_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        JSONArray result = new JSONArray();
        try {
            ProductionService productionService = ServiceFactory.getService(ProductionService.class);
            List<Production> productionList;
            
            if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
                String searchText = request.getParameter("name");
                int count;
                if (request.getParameter("count") != null && !request.getParameter("count").isEmpty()) {
                    count = DEFAULT_MAX_COUNT;
                } else {
                    count = Integer.parseInt(request.getParameter("count"));
                }
                productionList = productionService.findByName(searchText, count);
                for (Production production : productionList) {
                    JSONObject p = new JSONObject();
                    p.put("id",production.getId());
                    p.put("name", production.getName());
                    p.put("code", production.getCode());
                    result.add(p);
                }
            }
            productionService.close();
        } catch (DataException ex) {
            logger.error(ex.getMessage());
        }
        return ActionResult.json(result.toString());
    }
}
