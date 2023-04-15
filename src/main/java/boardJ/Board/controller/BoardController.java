package boardJ.Board.controller;

import boardJ.Board.dto.Board;
import boardJ.Board.dto.LoginInfo;
import boardJ.Board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // http 요청을 받아서 처리해주는 컴포넌트
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    // classpath:/templates/list.html 로 가게됨!
    public String list(@RequestParam(value = "page",defaultValue = "1") int page
            ,HttpSession session, Model model) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");

        int totalCount = boardService.getTotalCount(); // 12
        // log.info("totalCount는 ={}", totalCount);

        int pageCount = totalCount/10; // 1
        if(totalCount%10 > 0) {
            // 나머지가 있을경우 1page추가
            pageCount++;
        }
        int currentPage= page;
        List<Board> list = boardService.getBoards(page);
        // page가 1,2,3,4... 각 페이지 해당하는 내용들을 주기!
        model.addAttribute("logInInfo", loginInfo);
        model.addAttribute("list",list);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("currentPage",currentPage);
        return "list"; // 포워드함
    }
    @GetMapping("/board")
    public String board(@RequestParam("id") int boardId,HttpSession session , Model model ){
        // 해당 게시물을 읽어오고!
        // 조회수도 1증가시키기!

        // 제목이랑 컨텐츠 가져오기
        // 그리고 조회수 +1 하기!!
        Board board = boardService.getBoard(boardId);
        log.info("board id = {}",boardId);

        model.addAttribute("board", board);
        return "board";
    }

    @GetMapping("/writeForm")
    public String writeForm(HttpSession session, Model model) {
        // 로그인 한사람만 사용가능하게!!
        // session에서 로그인 정보 읽어들여야함!
        // userid email name
         LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
         if(loginInfo == null) {
             return "redirect:/loginform";
         }
         model.addAttribute("loginInfo", loginInfo);
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ){
        LoginInfo loginInfo =(LoginInfo) session.getAttribute("loginInfo");
        if(loginInfo == null) {
            return  "redirect:/loginform";
        }
        //log.info("title={}, content={}",title, content);
        boardService.addBoard(loginInfo.getUserId(),title,content);
        return "redirect:/";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("boardId") int boardId, HttpSession session) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) {
            return  "redirect:/loginform";
        }

        boardService.deleteBoard(loginInfo.getUserId(),boardId);
        return "redirect:/";
    }
}
