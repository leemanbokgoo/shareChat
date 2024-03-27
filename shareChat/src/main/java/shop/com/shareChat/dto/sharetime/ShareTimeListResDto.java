package shop.com.shareChat.dto.sharetime;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.util.DayOfWeekUtil;

import java.time.LocalTime;

@Getter
public class ShareTimeListResDto {

    private LocalTime startTime;
    private LocalTime endTime;
    private int shareChatTime;
    private String day;

    @Builder
    @QueryProjection
    public ShareTimeListResDto(LocalTime startTime, LocalTime endTime, int shareChatTime, DayOfWeek day) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.shareChatTime = shareChatTime;
        this.day = day.getKoreanName();
    }
}
