package zorc.action.report;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionResult;
import zorc.entity.PlanChange;
import zorc.exception.DataException;
import zorc.persistence.Constants;
import zorc.persistence.DateStringFormat;
import zorc.service.PlanChangeService;
import zorc.service.ServiceFactory;

/**
 * Действие по генерации отчета.
 * @author Genocide
 */
public class ReportGenerateAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ReportGenerateAction.class);
    
    @Override
    public String getName() {
        return Constants.REPORT_GENERATE_ACTION_NAME;
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {

            String selD = request.getParameter("selectedDate");// Дата фиксации, по которой будем делать отчет.
            String path = request.getParameter("path"); // Путь, куда бедем ложить отчет.
            Date date = Date.valueOf(DateStringFormat.logicalToDataBase(selD));

            PlanChangeService changeService = ServiceFactory.getService(PlanChangeService.class);
            PlanChange pch = changeService.findByDate(date);

            // Ложим в карту объекты, которые учавствуют в создании отчета.
            XLSTransformer transformer = new XLSTransformer();
            Map beans = new HashMap();
            beans.put("date", selD);
            beans.put("plans",pch.getPlans());
            
            // Это для того, чтобы добраться до файла в web/WEB-INF/classes/template.xls
            String template = Thread.currentThread().getContextClassLoader().getResource("template.xls").getFile();
            
            // Создаем отчет.
            transformer.transformXLS(template, beans,path);
            
            Constants.complete = true;// Запоминаем, что отчет успешно создан.
            changeService.close();
            
            return ActionResult.redirect(Constants.REPORT_PREPARE_ACTION_NAME);
        } catch (ParsePropertyException | IOException | DataException | InvalidFormatException ex) {
            // Небольшой мультикаст.
            logger.error(ex.getMessage());
            return ActionResult.redirect(Constants.ERROR_VIEW_ACTION_NAME);
        }
    }
}
