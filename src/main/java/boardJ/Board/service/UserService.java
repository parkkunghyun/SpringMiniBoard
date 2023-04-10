package boardJ.Board.service;

import boardJ.Board.dao.UserDao;
import boardJ.Board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 트랜잭션 단위로 실행될 메소드를 선언하고 있는 인터페이스!

// spring이 관리해주는 빈
@Service
// @RequiredArgsConstructor lombok이 final 필드를 초기화헤줌!
public class UserService {
    private final UserDao userDao;

    // Spring이 UserService를 Bean으로 생성할때 생성자를 이용
    // 이때 USerDao Bean이 있는지 보고 그 빈을 주입 -> 생성자 주입!!
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 보통 서비스에서는 하나의 트랜잭션으로 처리하기위해 @Transactional을 붙임
    // 스프링 부트가 트랜잭션 관리자를 가지고 있음
    @Transactional
    public User addUser(String name, String email, String password) {
        //
        User user = userDao.addUser(email,name,password);
        userDao.mappingUserRole(user.getUserId());// 권한을 부여
        return user;
    }

    @Transactional
    public User getUser(String email) {
        return userDao.getUser(email);
    }

}
