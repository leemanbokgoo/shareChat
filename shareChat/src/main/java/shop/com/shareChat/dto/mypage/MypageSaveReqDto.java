package shop.com.shareChat.dto.mypage;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.SavedRequest;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.user.User;

@RequiredArgsConstructor
@Getter
public class MypageSaveReqDto {

    @NotEmpty
    @Size(min = 1, max = 20)
    private String title;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{1,2}$")
    private int career;

    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,20}$")
    private String job;

    @NotEmpty
    @Size(min = 1, max = 1000)
    private String intro;

    public Mypage toEntity(User user){
        return Mypage.builder()
                .title(this.title)
                .career(this.career)
                .job(this.job)
                .intro(this.intro)
                .user(user)
                .build();
    }
}
