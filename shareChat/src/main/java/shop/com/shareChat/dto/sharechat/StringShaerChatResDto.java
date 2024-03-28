package shop.com.shareChat.dto.sharechat;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StringShaerChatResDto {
    private String startTime;
    private String endTime;

    @Builder
    @QueryProjection

    public StringShaerChatResDto(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
