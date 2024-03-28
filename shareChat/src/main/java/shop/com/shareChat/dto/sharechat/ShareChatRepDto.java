package shop.com.shareChat.dto.sharechat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import shop.com.shareChat.domain.sharechat.Sharechat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ShareChatRepDto {

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String content;

    public ShareChatRepDto(LocalTime startTime, LocalTime endTime, LocalDate date, String content) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.content = content;
    }

    public Sharechat toEntity(){
        return Sharechat.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .date(this.date)
                .content(this.content)
                .build();
    }
}
