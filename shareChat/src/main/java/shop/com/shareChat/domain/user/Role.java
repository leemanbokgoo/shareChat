package shop.com.shareChat.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER   ("ROLE_USER", "일반사용자"),
    ADMIN   ("ROLE_ADMIN", "관리자");


    private final String key;
    private final String title;

}
