package shop.com.shareChat.dto.mypage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
public class MypageUpdateReqDto {

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

    @Builder
    public MypageUpdateReqDto(String title, int career, String job, String intro) {
        this.title = title;
        this.career = career;
        this.job = job;
        this.intro = intro;
    }
}
