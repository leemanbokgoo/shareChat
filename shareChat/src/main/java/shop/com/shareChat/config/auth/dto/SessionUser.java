package shop.com.shareChat.config.auth.dto;

import lombok.Getter;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String email;
    private Role role;
    // 나중에 이미지 추가
//    private String picture;

    public SessionUser(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
