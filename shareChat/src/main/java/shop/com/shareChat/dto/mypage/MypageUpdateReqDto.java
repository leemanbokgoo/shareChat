package shop.com.shareChat.dto.mypage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class MypageUpdateReqDto {

    @NotEmpty
    @Size(min = 1, max = 20)
    private String title;

    private int career;
    private int occupation;

    @NotEmpty
    private String job;

    @NotEmpty
    @Size(min = 1, max = 1000)
    private String intro;

    @Builder
    public MypageUpdateReqDto(String title, int career, int occupation, String job, String intro) {
        this.title = title;
        this.career = career;
        this.occupation = occupation;
        this.job = job;
        this.intro = intro;
    }
}
