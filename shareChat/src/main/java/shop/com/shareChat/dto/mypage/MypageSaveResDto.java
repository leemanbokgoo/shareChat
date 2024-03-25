package shop.com.shareChat.dto.mypage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import shop.com.shareChat.domain.mypage.Mypage;

@Getter
public class MypageSaveResDto {
    private Long id;

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


    public MypageSaveResDto(Mypage mypage) {
        this.id = mypage.getId();
        this.title = mypage.getTitle();
        this.career = mypage.getCareer();
        this.job = mypage.getJob();
        this.intro = mypage.getIntro();
    }
}
