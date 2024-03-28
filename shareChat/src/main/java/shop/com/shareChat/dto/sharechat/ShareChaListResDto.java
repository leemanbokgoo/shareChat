package shop.com.shareChat.dto.sharechat;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
public class ShareChaListResDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private int shareChatTime;

    @Builder
    @QueryProjection
    public ShareChaListResDto(LocalTime startTime, LocalTime endTime, int shareChatTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.shareChatTime = shareChatTime;
    }
}
