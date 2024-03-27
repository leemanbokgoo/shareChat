package shop.com.shareChat.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.com.shareChat.domain.mypage.Occupation;

@RequiredArgsConstructor
@Setter
@Getter
public class MypageResDto {
    private Long id;
    private String job;
    private String title;
    private String intro;
    private String occupation;

    @Builder
    @QueryProjection
    public MypageResDto(Long id, String job, String title, String intro, int occupation) {
        String occupationByValue = String.valueOf(Occupation.getOccupationByValue(occupation));
        this.id = id;
        this.job = job;
        this.title = title;
        this.intro = intro;
        this.occupation = occupationByValue;
    }
}
