package shop.com.shareChat.dto.sharechat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import shop.com.shareChat.domain.sharechat.Sharechat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ShareChatResDto {

    private Long id;
    private LocalTime startTIme;
    private LocalTime endTime;
    private LocalDate date;
    @NotEmpty
    private String content;

    @Builder
    public ShareChatResDto(Sharechat sharechat) {
        this.id = sharechat.getId();
        this.startTIme = sharechat.getStartTime();
        this.endTime = sharechat.getEndTime();
        this.date = sharechat.getDate();
        this.content = sharechat.getContent();
    }
}