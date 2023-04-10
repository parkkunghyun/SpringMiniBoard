package boardJ.Board.controller;

import boardJ.Board.dto.LoginInfo;
import boardJ.Board.dto.User;
import boardJ.Board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


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
        // repository를 이용해서 엔티티로 회원가입 save해주려면
        // 폼도 파라미터로 오니까 이거 받기!
        // redirect:/welome
        log.info("name={}, email={}, password={}", name,email,password);

        User user = userService.addUser(name,email,password);
        log.info("user={}",user.getName());
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
            @RequestParam("password") String password,
            HttpSession httpSession){
        // email에 대한 회원 정보 읽어온후 세션에 맞으면 저장!
        log.info("login email={} password={}",email,password);

        try{
            User findUser=  userService.getUser(email);
            if(findUser.getPassword().equals(password)) {

                // LoginInfo에 넣어주기!
                LoginInfo loginInfo = new LoginInfo(findUser.getUserId(), findUser.getEmail(), findUser.getName());
                httpSession.setAttribute("loginInfo", loginInfo);
                // key - value
                log.info("session에도 로그인 정보가 저장!!");
            }else {
                // catch로 전달!
                throw  new RuntimeException("암호가 같지 않음");
            }
            //log.info("user={}",findUser);
        }catch (Exception e){
            // 정확하게 뭐가 틀린지 url에 보여주면 해킹 위험이 있다!
            return "redirect:/loginform?error=true";
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginInfo");
        // session에서 회원정보 삭제
        return "redirect:/";
    }
}
