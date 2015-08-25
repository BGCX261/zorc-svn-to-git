package zorc.service;

/**
 * Интерфейс для закрытия соединения с БД.
 * @author Genocide
 */
public interface CloseService extends Service {

    /**
     * Освободить(закрыть) соединение с БД.
     */
    void close();
}
