package boardJ.Board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class UserController {
    @GetMapping("/userRegForm")
    public String userRegForm(){
        return "userRegForm";
    }

    @PostMapping("/userReg")
    public String userReg(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        // reposity를 이용해서 엔티티로 회원가입 save해주려면
        // 폼도 파라미터로 오니까 이거 받기!
        // redirect:/welome
        log.info("name={}, email={}, password={}", name,email,password);
        return "redirect:/welcome";

    }
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/loginform")
    public String loginform() {
        return "loginform";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password){

        // email에 대한 회원 정보 읽어온후 세션에 맞으면 저장!
        log.info("login email={} password={}",email,password);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(){
        // session에서 회원정보 삭제
        return "redirect:/";
    }
}
