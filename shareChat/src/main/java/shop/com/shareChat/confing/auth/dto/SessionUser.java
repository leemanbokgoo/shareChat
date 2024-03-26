package shop.com.shareChat.confing.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String username;
    private Role role;
    // 나중에 이미지 추가
//    private String picture;

    public SessionUser(User user) {
        this.name = user.getNickname();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public SessionUser(SessionUser user){
        this.name = user.getName();
        this.username = user.getUsername();
    }
}
