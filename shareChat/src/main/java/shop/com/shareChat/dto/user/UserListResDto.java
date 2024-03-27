package shop.com.shareChat.dto.user;

import jakarta.validation.constraints.NotEmpty;
import shop.com.shareChat.domain.mypage.Occupation;

public class UserListResDto {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String job;

    @NotEmpty
    private int career;

    @NotEmpty
    private String intro;

    @NotEmpty
    private String occupation;

    public UserListResDto(String nickname, String job, int career, String intro, int occupation) {
        String occupationByValue = String.valueOf(Occupation.getOccupationByValue(occupation));

        this.nickname = nickname;
        this.job = job;
        this.career = career;
        this.intro = intro;
        this.occupation = occupationByValue;
    }
}
