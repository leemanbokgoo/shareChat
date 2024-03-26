package shop.com.shareChat.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import shop.com.shareChat.domain.user.User;

@Getter
public class JoinResDto{
    @NotEmpty
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,10}$")
    private String nickname;

    public JoinResDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
    }
}