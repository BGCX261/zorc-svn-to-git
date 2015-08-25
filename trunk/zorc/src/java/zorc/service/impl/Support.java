package zorc.service.impl;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zorc.persistence.TransactionManager;
import zorc.service.CloseService;

/**
 * @author Genocide. Вспомогательный класс для реализаций интерфейсов сервисов.
 */
abstract public class Support implements CloseService {

    private Logger log = LoggerFactory.getLogger(getClass());
    private TransactionManager manager = null;

    public Support(TransactionManager manager) {
        this.manager = manager;
    }

    public TransactionManager getManager() {
        return manager;
    }

    @Override
    public void close() {
        try {
            manager.close();
        } catch (SQLException e) {
            log.error("Ошибка при закрытии(освобождении) соединения!", e);
        }
    }
}