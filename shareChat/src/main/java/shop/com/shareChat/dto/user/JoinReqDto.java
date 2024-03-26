package shop.com.shareChat.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

@Getter
public class JoinReqDto{
    // 유효성 검사
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,10}$")
    private String nickname;

    @NotEmpty
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String username;

    @Builder
    public JoinReqDto(String nickname, String password, String username) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    // 회원가입 및 패스워드 인코딩
    public User toEntity(BCryptPasswordEncoder passwordEncoder){
        return User.builder()
                .nickname(nickname)
                .passwrod(passwordEncoder.encode(password))
                .username(username)
                .role(Role.USER)
                .build();
    }
}