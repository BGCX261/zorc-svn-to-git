package zorc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Общий интерфейс для всех действий.
 * @author Genocide
 */
public interface Action {

    /**
     * Получить имя действия.
     * @return Имя действия.
     */
    String getName();

    /**
     * Выполнить действие.
     * @param request Объект типа HttpServletRequest.
     * @param response Объект типа HttpServletResponse.
     * @return Объект типа ActionResult.
     */
    ActionResult execute(HttpServletRequest request, HttpServletResponse response);
}
