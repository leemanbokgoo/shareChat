package shop.com.shareChat.dto.sharechat;

import jakarta.validation.constraints.NotEmpty;
import shop.com.shareChat.domain.sharechat.Sharechat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShareChatRepDto {

    private LocalTime startTIme;
    private LocalTime endTime;
    private LocalDateTime date;
    @NotEmpty
    private String content;

    public Sharechat toEntity(){
        return Sharechat.builder()
                .startTime(this.startTIme)
                .endTime(this.endTime)
                .date(this.date)
                .content(this.content)
                .build();
    }
}
