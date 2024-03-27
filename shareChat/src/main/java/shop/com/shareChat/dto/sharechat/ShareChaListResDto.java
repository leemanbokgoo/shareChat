package shop.com.shareChat.dto.sharechat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
public class ShareChaListResDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime date;
    private String content;

    @Builder
    public ShareChaListResDto(LocalTime startTime, LocalTime endTime, LocalDateTime date, String content) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.content = content;
    }
}
