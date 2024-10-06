package prodao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MaintenanceDAO {

    public JdbcTemplate jdbcTemplate;

    public MaintenanceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listMethod(){
        String sql = "Select * from table";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }
}
