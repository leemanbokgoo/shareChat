package shop.com.shareChat.domain.shartime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sharetime_sc")
@Entity
public class Sharetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private DayOfWeek day;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false, length = 1000)
    private int shareChatTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Builder
    public Sharetime(Long id, DayOfWeek day, LocalTime endTime, LocalTime startTime, int shareChatTime, User user, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.day = day;
        this.endTime = endTime;
        this.startTime = startTime;
        this.shareChatTime = shareChatTime;
        this.user = user;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Sharetime update( ShareTimeReqDto reqDto){
        this.startTime = reqDto.getStartTime();
        this.endTime = reqDto.getEndTime();
        this.shareChatTime = reqDto.getShareChatTime();
        this.day = reqDto.getDay();
        this.updatedAt = LocalDateTime.now();
        return this;
    }
}
