package shop.com.shareChat.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String nickname){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .id(1L)
                .nickname(nickname)
                .email(nickname + "@naver.com")
                .passwrod(encPassword)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    protected User newMockUser(Long id, String nickname){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .nickname(nickname)
                .passwrod(encPassword)
                .email(nickname+"@nate.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    protected Mypage newMockMypage(Long id, String title, String job){
        return Mypage.builder()
                .id(id)
                .title(title)
                .job(job)
                .career(3)
                .intro("안녕하세요")
                .state(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
