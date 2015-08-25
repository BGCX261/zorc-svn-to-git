package zorc.action;

import java.util.HashMap;
import java.util.Map;

/**
 * Менеджер действий.
 * @author Genocide
 */
public class ActionManager {

    // Список всех действий.
    private Map<String, Action> actionMap = new HashMap<>();

    /**
     * Добавить действие в список действий.
     * @param action Объект добавляемого дествия.
     */
    public void addAction(Action action) {
        actionMap.put(action.getName(), action);
    }

    /**
     * Посик действия по имени.
     * @param name Имя действия.
     * @return Если нашли действие - вернуть его, иначе NULL.
     */
    public Action findAction(String name) {
        return actionMap.get(name);
    }
}
