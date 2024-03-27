package shop.com.shareChat.dto.mypage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.user.User;

@RequiredArgsConstructor
@Getter
public class MypageSaveReqDto {

    @NotEmpty
    @Size(min = 1, max = 20)
    private String title;

    private int career;

    @NotEmpty
    private String job;

    private int occupation;

    @NotEmpty
    @Size(min = 1, max = 1000)
    private String intro;

    @Builder
    public MypageSaveReqDto(String title, int career, String job, int occupation, String intro) {
        this.title = title;
        this.career = career;
        this.job = job;
        this.occupation = occupation;
        this.intro = intro;
    }

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
