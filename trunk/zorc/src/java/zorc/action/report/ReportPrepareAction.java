package zorc.action.report;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.PlanChange;
import zorc.persistence.Constants;
import zorc.service.PlanChangeService;
import zorc.service.ServiceFactory;


/**
 * Действие по подготовке отчета.
 * @author Genocide
 */
public class ReportPrepareAction implements Action{
    
    private static final Logger logger = LoggerFactory.getLogger(ReportPrepareAction.class);

    @Override
    public String getName() {
        return Constants.REPORT_PREPARE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            List<PlanChange> fixChangeList = changeService.findFixed();
            request.setAttribute("fixChangeList", fixChangeList);
            
            // Если данное действие выполняется после создания отчета - 
            // ставим аттрибут complete в yes, чтобы вывести сообщение об успешности действия.
            if(Constants.complete){
                request.setAttribute("complete", "yes");
                // и сразу сбрасываем флаг, для следующего действия.
                Constants.complete = false;
            }
            changeService.close();
            return ActionResult.forward(Constants.REPORT_PREPARE_PAGE_PATH);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
    
}
