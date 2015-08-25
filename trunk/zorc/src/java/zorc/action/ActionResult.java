package zorc.action;

/**
 * Класс для работы с результатом действия.
 * @author Genocide
 */
public class ActionResult {

    // Режимы поведения после выполнения действия.
    private final static int MODE_REDIRECT = 0;
    private final static int MODE_FORWARD = 1;
    private final static int MODE_JSON = 2;
    
    private int mode;
    // Вспомогательная переменная.
    private String string;

    private ActionResult(int mode, String string) {
        this.mode = mode;
        this.string = string;
    }

    public String getAction() {
        return string;
    }

    public String getViewJsp() {
        return string;
    }
    
    public String getData() {
        return string;
    }

    public boolean isRedirect() {
        return mode == MODE_REDIRECT;
    }

    public boolean isForward() {
        return mode == MODE_FORWARD;
    }
    
    public boolean isJSONData() {
        return mode == MODE_JSON;
    }

    public static ActionResult forward(String viewJsp) {
        return new ActionResult(MODE_FORWARD, viewJsp);
    }

    public static ActionResult redirect(String actionName) {
        return new ActionResult(MODE_REDIRECT, actionName);
    }
    
    public static ActionResult json(String data) {
        return new ActionResult(MODE_JSON, data);
    }
}
