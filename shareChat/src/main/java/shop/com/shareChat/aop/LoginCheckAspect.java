package shop.com.shareChat.aop;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;

import java.util.Optional;

@Component
@Aspect
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // CheckLogin 어노테이션이 붙은 메소드가 실행하기 전 해당 로직을 실행
    @Before("@annotation(shop.com.shareChat.aop.annotation.LoginCheck ) && !!within(shop.com.shareChat.test..*))")
    public void checkLogin() throws HttpClientErrorException {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        Optional<User> userOP = userRepository.findByUsername(user.getUsername());
        // 사용자가 없는 경우 에러 발생
        if (userOP.isEmpty()) {
            throw new CustomApiException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
