package zorc.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Обший интерфейс для конвертации ResultSet'а в объект.
 *
 * @author Genocide
 * @param <T> Тип выходного объекта.
 */
public interface RowMapper<T> {

    /**
     * Конвертировать ResultSet в объект.
     *
     * @param rs Результат выполнения запроса(ResultSet).
     * @return Объект, созданный по ResultSet'у.
     * @throws SQLException Обращение к несуществующему столбцу в ResultSet'е.
     */
    T mapRow(ResultSet rs) throws SQLException;
}
