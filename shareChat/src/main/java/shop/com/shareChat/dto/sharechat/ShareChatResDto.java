package shop.com.shareChat.dto.sharechat;

import jakarta.validation.constraints.NotEmpty;
import shop.com.shareChat.domain.sharechat.Sharechat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShareChatResDto {

    private Long id;
    private LocalTime startTIme;
    private LocalTime endTime;
    private LocalDateTime date;
    @NotEmpty
    private String content;

    public ShareChatResDto(Sharechat sharechat) {
        this.id = sharechat.getId();
        this.startTIme = sharechat.getStartTime();
        this.endTime = sharechat.getEndTime();
        this.date = sharechat.getDate();
        this.content = sharechat.getContent();
    }
}