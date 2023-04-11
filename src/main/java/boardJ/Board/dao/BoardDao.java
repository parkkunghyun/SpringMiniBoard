package boardJ.Board.dao;

import boardJ.Board.dto.Board;
import boardJ.Board.dto.LoginInfo;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertBoard;

    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertBoard = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("board_id");
    }

    @Transactional
    public void addBoard(int userId, String title, String content) {
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setContent(content);
        board.setRegdate(LocalDateTime.now());
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        insertBoard.execute(params);
    }

    @Transactional(readOnly = true)
    public int getTotalCount() {
        String sql = "select count(*) as total_count from board"; // 무조건 한건만!
        Integer totalCount=  jdbcTemplate.queryForObject(sql, Map.of(),Integer.class); //비어있는 Map하나 파라미터로!
        return totalCount.intValue();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        //start값은 0, 10,20,30
        int start = (page -1) * 10;
        String sql = "select b.user_id,b.board_id,b.title, b.regdate, b.view_cnt, u.name  from board b, user u where b.user_id = u.user_id order by board_id desc limit :start, 10";

        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        List<Board> list = jdbcTemplate.query(sql,Map.of("start", start),rowMapper );
        return list;
    }

    @Transactional(readOnly = true)
    public Board getBoard(int id) {
        // 여기서 id가 boardID여야함!!
        // 값이 1개 또는 0개
        String sql = "select b.user_id, b.board_id, u.name, b.title, b.content, b.view_cnt from board b, user u where b.user_id = u.user_id AND b.board_id=:boardId";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        //SqlParameterSource params = new MapSqlParameterSource("boardId", id);
        return jdbcTemplate.queryForObject(sql,Map.of("boardId",id) ,rowMapper);
    }
    @Transactional
    public void updateViewCount(int id) {
        String sql = "update board set view_cnt=view_cnt+1 where board_id = :boardId";
        jdbcTemplate.update(sql, Map.of("boardId", id));
    }

    @Transactional
    public void deleteBoard(int boardId) {
        String sql ="delete from board where board_id =:boardId";

        jdbcTemplate.update(sql,Map.of("boardId", boardId));
    }
}
