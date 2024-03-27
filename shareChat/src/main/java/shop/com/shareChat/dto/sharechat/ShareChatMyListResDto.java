package shop.com.shareChat.dto.sharechat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ShareChatMyListResDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String nickname;
    private String title;

    @Builder
    public ShareChatMyListResDto(LocalTime startTime, LocalTime endTime, LocalDate date, String nickname, String title) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.nickname = nickname;
        this.title = title;
    }
}
