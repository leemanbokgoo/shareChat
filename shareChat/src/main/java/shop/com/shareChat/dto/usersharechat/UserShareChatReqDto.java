package shop.com.shareChat.dto.usersharechat;

import lombok.Builder;
import lombok.Getter;
import shop.com.shareChat.domain.sharechat.Sharechat;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.userSharchat.UserSharechat;

@Getter
public class UserShareChatReqDto {

    private User user;
    private User receiver;
    private Sharechat sharechat;

    @Builder
    public UserShareChatReqDto(User user, User receiver, Sharechat sharechat) {
        this.user = user;
        this.receiver = receiver;
        this.sharechat = sharechat;
    }

    public UserSharechat toEntity(){
        return UserSharechat.builder()
                .user(user)
                .receiver(receiver)
                .sharechat(sharechat)
                .build();
    }
}
