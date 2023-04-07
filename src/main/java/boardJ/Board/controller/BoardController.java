package boardJ.Board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // http 요청을 받아서 처리해주는 컴포넌트
@Slf4j
public class BoardController {
    @GetMapping("/")
    // classpath:/templates/list.html 로 가게됨!
    //
    public String list() {
        // 게시물 목록 보여주는 곳

        return "list"; // 포워드함
    }
    @GetMapping("/board")
    public String board(@RequestParam("id") int id ){
        // 해당 게시물을 읽어오고!
        // 조회수도 1증가시키기!
        return "board";
    }
}
