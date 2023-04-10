package boardJ.Board.controller;

import boardJ.Board.dto.LoginInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // http 요청을 받아서 처리해주는 컴포넌트
@Slf4j
public class BoardController {
    @GetMapping("/")
    // classpath:/templates/list.html 로 가게됨!
    //
    public String list(HttpSession session, Model model) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");

        // null이나 아니냐
        model.addAttribute("logInInfo", loginInfo);
        // 게시물 목록 보여주는 곳

        return "list"; // 포워드함
    }
    @GetMapping("/board")
    public String board(@RequestParam("id") int id ){
        // 해당 게시물을 읽어오고!
        // 조회수도 1증가시키기!
        return "board";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        // 로그인 한사람만 사용가능하게!!
        // session에서 로그인 정보 읽어들여야함!

        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ){
        log.info("title={}, content={}",title, content);
        return "redirect:/";
    }
}
