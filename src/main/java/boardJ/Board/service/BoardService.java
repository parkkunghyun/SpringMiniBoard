package boardJ.Board.service;

import boardJ.Board.dao.BoardDao;
import boardJ.Board.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDao boardDao;

    @Transactional
    public void addBoard(int userId, String title, String content) {
        // 넘어온 인자로 dao를 이용해서 데이터넣기@
        boardDao.addBoard(userId,title,content);

    }

    @Transactional(readOnly = true) // select는 이렇게 넣어야 성능 좋음!
    public int getTotalCount() {
        return boardDao.getTotalCount();
    }
    // 게시판 글을 추가하고 수정하고 조회하기

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        return boardDao.getBoards(page);
    }

    @Transactional
    public Board getBoard(int id) {
        // 가져오고 조회수 증가!
        Board board = boardDao.getBoard(id);
        boardDao.updateViewCount(id);

        return board;
    }

    @Transactional
    public void deleteBoard(int userId, int boardId) {
        Board board = boardDao.getBoard(boardId);
        if(board.getUserId() == userId) {
            boardDao.deleteBoard(boardId);
        }
    }
}
