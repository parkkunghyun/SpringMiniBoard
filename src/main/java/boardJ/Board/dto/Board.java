package boardJ.Board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class Board {
    private int boardId;
    private String title;
    private String content;
    private int userId;
    private LocalDateTime regdate;
    private int viewCnt;

    private String name; // join을 위해 일부러 추가!


}
