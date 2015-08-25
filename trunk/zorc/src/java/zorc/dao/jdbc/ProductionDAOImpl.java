package zorc.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import zorc.dao.ProductionDAO;
import zorc.entity.Production;
import zorc.exception.DataException;

/**
 * @author Genocide
 * @author rsd
 *
 * Реализация ДАО для продукции.
 */
public class ProductionDAOImpl extends JdbcDaoSupport implements ProductionDAO {

    private RowMapper<Production> rowMapp = new RowMapper<Production>() {
        @Override
        public Production mapRow(ResultSet rs) throws SQLException {
            Production prod = new Production();

            prod.setId(rs.getInt("id"));
            prod.setCode(rs.getString("code"));
            prod.setName(rs.getString("name"));

            return prod;
        }
    };

    public ProductionDAOImpl(Connection con) {
        super(con);
    }

    @Override
    public Integer create(Production entity) throws DataException {
        return queryForCreate("INSERT INTO `production`(`code`, `name`) VALUES (?,?);",
                entity.getCode(),
                entity.getName());
    }

    @Override
    public Production read(Integer id) throws DataException {
        return queryForObject("SELECT * FROM `production` WHERE `id` = ?;", rowMapp, id);
    }

    @Override
    public void update(Production entity) throws DataException {
        update("UPDATE `production` SET `code` = ?,`name` = ? WHERE `id` = ?;",
                entity.getCode(),
                entity.getName(),
                entity.getId());
    }

    @Override
    public void delete(Integer id) throws DataException {
        update("DELETE FROM `production` WHERE `id` = ?;", id);
    }

    @Override
    public List<Production> readAll() throws DataException {
        return queryForList("SELECT * FROM `production`;", rowMapp);

    }

    @Override
    public List<Production> findByName(String name, int count) throws DataException {
        String limit = ";";
        if (count > 0) {
            limit = "LIMIT " + count + limit;
        }
        return queryForList("SELECT * FROM `production` WHERE "
                + "lower(`name`) LIKE ? " + limit, rowMapp, "%" + name.toLowerCase() + "%");
    }

    @Override
    public List<Production> findByCode(String code, int count) throws DataException {
        String limit = ";";
        if (count > 0) {
            limit = "LIMIT " + count + limit;
        }
        return queryForList("SELECT * FROM `production` WHERE "
                + "lower(`code`) LIKE ? " + limit, rowMapp, "%" + code.toLowerCase() + "%");
    }
}
