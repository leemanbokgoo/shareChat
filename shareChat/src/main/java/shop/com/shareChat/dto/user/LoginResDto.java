package shop.com.shareChat.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.util.CustomDateUtil;

@Getter
@Setter
public class LoginResDto {
    @NotEmpty
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String username;
    private String createdAt;

    public LoginResDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.createdAt = CustomDateUtil.toStrnigFormat(user.getCreatedAt());
    }
}
