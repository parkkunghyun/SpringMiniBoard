package boardJ.Board.dao;

import boardJ.Board.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertUser;
    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");
    }
    // Spring JDBC를 이용한 코드 작성!
    @Transactional
    public User addUser(String email,String name, String password) {
        // service에서 이미 트랜잭션이 시작되어 부른거여서 애는 service의 트랜잭션에 포함

        //insert into user(email,name, password, regdate) values (:email,?,?, now());
        //SELECT LAST_INSERT_ID();
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setRegdate(LocalDateTime.now());

        SqlParameterSource params = new BeanPropertySqlParameterSource(
                user
        );
        Number number = insertUser.executeAndReturnKey(params);

        int userId = number.intValue();
        user.setUserId(userId);
        return user;
    }

    @Transactional
    public void mappingUserRole(int userId){
        //insert into user_role(user_id, role_id) values (?,1);
        String sql = "insert into user_role(user_id, role_id) values (:userId,1)";
        SqlParameterSource params = new MapSqlParameterSource("userId",userId);
        jdbcTemplate.update(sql,params);

    }

    @Transactional
    public User getUser(String email) {
        // user_id -> setUsetId 등으로 각각 매핑됨!
        String sql = "select * from user where email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email);

        RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        User user = jdbcTemplate.queryForObject(sql,params,rowMapper );
        return user;
    }
}
