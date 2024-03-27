package shop.com.shareChat.dto.user;

import lombok.Getter;

@Getter
public class UserResDto {

    private Long id;
    private String nickname;
    private String title;
    private int occupation;
    private String job;
    private String carrer;
    private String intro;

    public UserResDto(Long id, String nickname, String title, int occupation, String job, String carrer, String intro) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.occupation = occupation;
        this.job = job;
        this.carrer = carrer;
        this.intro = intro;
    }
}
