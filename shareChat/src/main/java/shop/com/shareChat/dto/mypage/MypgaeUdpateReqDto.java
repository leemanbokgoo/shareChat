package shop.com.shareChat.dto.mypage;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MypgaeUdpateReqDto {

    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;
    private int shareChatTime;
    public MypgaeUdpateReqDto(LocalTime startTime, LocalTime endTime, DayOfWeek day, int shareChatTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.shareChatTime = shareChatTime;
    }
}
