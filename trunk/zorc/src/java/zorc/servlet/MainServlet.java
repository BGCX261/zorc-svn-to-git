package zorc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.action.Action;
import zorc.action.ActionManager;
import zorc.action.ActionResult;
import zorc.action.common.DefaultAction;
import zorc.action.common.ErrorAction;
import zorc.action.common.SettingsAction;
import zorc.action.plan.PlanCreateAction;
import zorc.action.plan.PlanDeleteAction;
import zorc.action.plan.PlanEditAction;
import zorc.action.plan.PlanListAction;
import zorc.action.plan.PlanSaveAction;
import zorc.action.planchange.PlanChangeFixAction;
import zorc.action.planchange.PlanChangeListAction;
import zorc.action.production.ProductionCreateAction;
import zorc.action.production.ProductionDeleteAction;
import zorc.action.production.ProductionEditAction;
import zorc.action.production.ProductionFindAction;
import zorc.action.production.ProductionListAction;
import zorc.action.production.ProductionSaveAction;
import zorc.action.report.ReportGenerateAction;
import zorc.action.report.ReportPrepareAction;
import zorc.action.unit.UnitCreateAction;
import zorc.action.unit.UnitDeleteAction;
import zorc.action.unit.UnitEditAction;
import zorc.action.unit.UnitListAction;
import zorc.action.unit.UnitSaveAction;
import zorc.exception.DataException;
import zorc.persistence.ConnectionPool;

/**
 * Главный сервлет.
 * @author Genocide
 */
public class MainServlet extends HttpServlet {

    private ActionManager actionManager;
    private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {

        logger.info("Main servlet initializing..");

        try {
            String url = getServletConfig().getInitParameter("url");
            String login = getServletConfig().getInitParameter("login");
            String password = getServletConfig().getInitParameter("password");
            String driver = getServletConfig().getInitParameter("driver");
            int cpSize = Integer.parseInt(getServletConfig().getInitParameter("cpsize"));

            ConnectionPool.init(url, login, password, driver, cpSize);
        } catch (DataException e) {
            logger.error("Ошибка инициализации Connection Pool'а!",e);
           throw new RuntimeException(e);
        }

        actionManager = new ActionManager();

        actionManager.addAction(new PlanCreateAction());
        actionManager.addAction(new PlanDeleteAction());
        actionManager.addAction(new PlanEditAction());
        actionManager.addAction(new PlanListAction());
        actionManager.addAction(new PlanSaveAction());
        
        actionManager.addAction(new PlanChangeFixAction());
        actionManager.addAction(new PlanChangeListAction());

        actionManager.addAction(new ProductionCreateAction());
        actionManager.addAction(new ProductionDeleteAction());
        actionManager.addAction(new ProductionEditAction());
        actionManager.addAction(new ProductionListAction());
        actionManager.addAction(new ProductionSaveAction());
        actionManager.addAction(new ProductionFindAction());

        actionManager.addAction(new UnitCreateAction());
        actionManager.addAction(new UnitDeleteAction());
        actionManager.addAction(new UnitEditAction());
        actionManager.addAction(new UnitListAction());
        actionManager.addAction(new UnitSaveAction());
        
        actionManager.addAction(new ReportPrepareAction());
        actionManager.addAction(new ReportGenerateAction());

        actionManager.addAction(new SettingsAction());
        actionManager.addAction(new ErrorAction());
        actionManager.addAction(new DefaultAction());
    }

    @Override
    public void destroy() {
        ConnectionPool.close();
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String actionName = request.getParameter("action");
        // Если действие не распознано - то выполняется действие по умолчанию.
        if ((actionName == null) || (actionManager.findAction(actionName) == null)) {
            actionName = "default.action";
        }

        Action action = actionManager.findAction(actionName);

        ActionResult ar;
        try {
            ar = action.execute(request, response);

            // В зависимости от результата выполняем нужные действия.
            if (ar.isForward()) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/View/" + ar.getViewJsp());
                requestDispatcher.forward(request, response);
            }
            if (ar.isRedirect()) {
                response.sendRedirect(request.getContextPath() + "/main?action=" + ar.getAction());
            }
            if (ar.isJSONData()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");  
                response.getWriter().print(ar.getData());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/View/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
