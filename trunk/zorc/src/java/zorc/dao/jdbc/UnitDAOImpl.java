package zorc.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import zorc.dao.UnitDAO;
import zorc.entity.Unit;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Реализация ДАО для единиц измерения.
 */
public class UnitDAOImpl extends JdbcDaoSupport implements UnitDAO {

    private RowMapper<Unit> rowMapp = new RowMapper<Unit>() {
        @Override
        public Unit mapRow(ResultSet rs) throws SQLException {
            Unit u = new Unit();

            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setSymbol(rs.getString("symbol"));

            return u;
        }
    };

    public UnitDAOImpl(Connection con) {
        super(con);
    }

    @Override
    public Integer create(Unit entity) throws DataException {
        return queryForCreate("INSERT INTO `unit`(`name`, `symbol`) VALUES (?,?);",
                entity.getName(),
                entity.getSymbol());
    }

    @Override
    public Unit read(Integer id) throws DataException {
        return queryForObject("SELECT * FROM `unit` WHERE `id` = ?;", rowMapp, id);
    }

    @Override
    public void update(Unit entity) throws DataException {
        update("UPDATE `unit` SET `name` = ?,`symbol` = ? WHERE `id` = ?;",
                entity.getName(),
                entity.getSymbol(),
                entity.getId());
    }

    @Override
    public void delete(Integer id) throws DataException {
        update("DELETE FROM `unit` WHERE `id` = ?;", id);
    }

    @Override
    public List<Unit> readAll() throws DataException {
        return queryForList("SELECT * FROM `unit`;", rowMapp);
    }

    @Override
    public Unit findBySymbol(String symbol) throws DataException {
        return queryForObject("SELECT `id`,`name`,`symbol` FROM `unit` WHERE `symbol` = ?;", rowMapp, symbol);
    }
}
