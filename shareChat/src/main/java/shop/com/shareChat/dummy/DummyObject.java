package shop.com.shareChat.dummy;

import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.sharechat.Sharechat;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DummyObject {

    protected User newUser(String nickname){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .id(1L)
                .nickname(nickname)
                .username(nickname + "@naver.com")
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
                .username(nickname+"@naver.com")
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

    protected Sharetime newMockShareTime(){
        LocalTime date = LocalTime.now();

        return Sharetime.builder()
                .endTime(date)
                .startTime(date)
                .day(DayOfWeek.FRIDAY)
                .shareChatTime(30)
                .user(newMockUser(1L, "test"))
                .build();
    }

    protected Sharechat newMockShareChat(String content){
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();

        return Sharechat.builder()
                .content(content)
                .endTime(time)
                .startTime(time)
                .date(date)
                .state(2)
                .updatedAt(dateTime)
                .createdAt(dateTime)
                .build();
    }
}
