package zorc.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import zorc.dao.PlanDAO;
import zorc.entity.Plan;
import zorc.entity.PlanChange;
import zorc.entity.Production;
import zorc.entity.Unit;
import zorc.exception.DataException;

/**
 * @author Genocide
 *
 * Реализация ДАО для плана закупки.
 */
public class PlanDAOImpl extends JdbcDaoSupport implements PlanDAO {

    private final RowMapper<Plan> rowMapp = new RowMapper<Plan>() {
        @Override
        public Plan mapRow(ResultSet rs) throws SQLException {
            Plan plan = new Plan();

            plan.setId(rs.getInt("id"));
            plan.setPosition(rs.getInt("position"));

            Production p = new Production();
            p.setId(rs.getInt("product_id"));
            plan.setName(rs.getString("name"));
            plan.setProduct(p);

            plan.setPrice(rs.getDouble("price"));
            plan.setAmount(rs.getDouble("amount"));
            plan.setBegin(rs.getDate("begin"));
            plan.setEnd(rs.getDate("end"));

            Unit u = new Unit();
            u.setId(rs.getInt("unit_id"));
            plan.setUnit(u);

            plan.setComment(rs.getString("comment"));
            
            PlanChange pc = new PlanChange();
            pc.setId(rs.getInt("plan_change_id"));
            plan.setChange(pc);

            return plan;
        }
    };

    public PlanDAOImpl(Connection con) {
        super(con);
    }

    @Override
    public Integer create(Plan entity) throws DataException {
        return queryForCreate("INSERT INTO `plan` (`position`, `product_id`,"
                + "`name`, `price`, `amount`, `begin`, `end`, `unit_id`,"
                + "`comment`, `plan_change_id`)  VALUES (?,?,?,?,?,?,?,?,?,?);",
                entity.getPosition(),
                entity.getProduct().getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getAmount(),
                entity.getBegin(),
                entity.getEnd(),
                entity.getUnit().getId(),
                entity.getComment(),
                entity.getChange().getId());
    }

    @Override
    public Plan read(Integer id) throws DataException {
        return queryForObject("SELECT `id`, `position`, `product_id`, `name`,"
                + " `price`, `amount`, `begin`, `end`, `unit_id`, `comment`,"
                + " `plan_change_id` FROM `plan` WHERE `id` = ?;", rowMapp, id);
    }

    @Override
    public void update(Plan entity) throws DataException {
        update("UPDATE `plan` SET `position`=?, `product_id`=?, `name`=?,"
                + " `price`=?, `amount`=?, `begin`=?, `end`=?, `unit_id`=?,"
                + " `comment`=?, `plan_change_id`=? WHERE `id` = ?;",
                entity.getPosition(),
                entity.getProduct().getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getAmount(),
                entity.getBegin(),
                entity.getEnd(),
                entity.getUnit().getId(),
                entity.getComment(),
                entity.getChange().getId(),
                entity.getId());
    }

    @Override
    public void delete(Integer id) throws DataException {
        update("DELETE FROM `plan` WHERE `id` = ?;", id);
    }

    @Override
    public List<Plan> readAll() throws DataException {
        return queryForList("SELECT * FROM `plan`;", rowMapp);
    }

    @Override
    public List<Plan> findByName(String name) throws DataException {
        return queryForList("SELECT * FROM `plan` WHERE lower(`name`) LIKE ? ;",
                rowMapp, "%" + name.toLowerCase() + "%");
    }

    @Override
    public List<Plan> findByDate(Date date) throws DataException {
        return queryForList("SELECT * FROM `plan`"
                + " WHERE `begin`<= ? AND `end`>= ? ;", rowMapp, date, date);
    }

    @Override
    public Integer getNextPosition(PlanChange change) throws DataException {
        return queryForInteger("SELECT Max(`position`) FROM `plan`"
                + " WHERE `plan_change_id` = ?;", change.getId()) + 1;
    }

    @Override
    public List<Plan> findByChange(PlanChange change) throws DataException {
        return queryForList("SELECT * FROM `plan` WHERE `plan_change_id` = ?;",
                rowMapp, change.getId());
    }
}
