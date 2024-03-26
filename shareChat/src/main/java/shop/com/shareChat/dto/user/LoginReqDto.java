package shop.com.shareChat.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import shop.com.shareChat.domain.user.User;

@Setter
@Getter
public class LoginReqDto{
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String username;
    @NotEmpty
    private String password;

    public LoginReqDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPasswrod();
    }
}
