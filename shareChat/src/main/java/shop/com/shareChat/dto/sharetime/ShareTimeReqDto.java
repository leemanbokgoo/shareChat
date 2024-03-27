package shop.com.shareChat.dto.sharetime;

import lombok.Builder;
import lombok.Getter;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;

import java.time.LocalTime;

@Getter
public class ShareTimeReqDto {

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int shareChatTime;
    User user;

    @Builder
    public ShareTimeReqDto(DayOfWeek day, LocalTime endTime, LocalTime startTime, int shareChatTime, User user) {
        this.day = day;
        this.endTime = endTime;
        this.startTime = startTime;
        this.shareChatTime = shareChatTime;
        this.user = user;
    }

    public Sharetime toEntity(User user){
        return Sharetime.builder()
                .day(this.day)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .shareChatTime(this.shareChatTime)
                .user(user)
                .build();
    }
}
