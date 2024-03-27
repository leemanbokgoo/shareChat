package shop.com.shareChat.dto.sharetime;

import lombok.Getter;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ShareTimeResDto {
    private Long id;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int shareChatTime;

    public ShareTimeResDto(Sharetime sharetime) {
        this.id = sharetime.getId();
        this.day = sharetime.getDay();
        this.startTime = sharetime.getStartTime();
        this.endTime = sharetime.getEndTime();
        this.shareChatTime = sharetime.getShareChatTime();
    }
}
