package zorc.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import zorc.dao.PlanChangeDAO;
import zorc.entity.PlanChange;
import zorc.exception.DataException;

/**
 * @author rsd
 *
 * Реализация ДАО для изменения плана.
 */
public class PlanChangeDAOImpl extends JdbcDaoSupport implements PlanChangeDAO {

    private RowMapper<PlanChange> rowMapp = new RowMapper<PlanChange>() {
        @Override
        public PlanChange mapRow(ResultSet rs) throws SQLException {
            PlanChange planChange = new PlanChange();
            planChange.setId(rs.getInt("id"));
            planChange.setDate(rs.getDate("date"));
            return planChange;
        }
    };

    public PlanChangeDAOImpl(Connection con) {
        super(con);
    }

    @Override
    public Integer create(PlanChange entity) throws DataException {
        return queryForCreate("INSERT INTO `plan_change` (`date`) VALUES (?);",
                entity.getDate());
    }

    @Override
    public PlanChange read(Integer id) throws DataException {
        return queryForObject("SELECT `id`, `date` FROM `plan_change` "
                + "WHERE `id` = ?;", rowMapp, id);
    }

    @Override
    public void update(PlanChange entity) throws DataException {
        update("UPDATE `plan_change` SET `date`=? WHERE `id` = ?;",
                entity.getDate(), entity.getId());
    }

    @Override
    public void delete(Integer id) throws DataException {
        update("DELETE FROM `plan_change` WHERE `id` = ?;", id);
    }

    @Override
    public List<PlanChange> readAll() throws DataException {
        return queryForList("SELECT * FROM `plan_change`;", rowMapp);
    }

    @Override
    public PlanChange findByDate(Date date) throws DataException {
        return queryForObject("SELECT * FROM `plan_change` "
                + "WHERE TIMESTAMPDIFF(Day, `date`, ?) = 0 ;", rowMapp, date);
    }

    @Override
    public PlanChange findUnfixed() throws DataException {
        return queryForObject("SELECT * FROM `plan_change` "
                + "WHERE `date` IS NULL ;", rowMapp);
    }

    @Override
    public PlanChange getPrevious(PlanChange entity) throws DataException {
        return queryForObject("SELECT * FROM `plan_change` WHERE `date` ="
                + " (select max(`date`) FROM `plan_change` WHERE `date`<?);",
                rowMapp, (entity.getDate() == null)
                        ? "=CURRENT_DATE()"
                        : entity.getDate());
    }
    
    @Override
    public Integer getNextId(PlanChange change) throws DataException {
        Integer result = queryForInteger("SELECT Min(`id`) FROM `plan_change`"
                + " WHERE `id` > ?;", change.getId());
        return result == 0 ? null : result;
    }

    @Override
    public List<PlanChange> findFixed() throws DataException {
        return queryForList("SELECT * FROM `plan_change` "
                + "WHERE `date` IS NOT NULL ;", rowMapp);
    }
}
