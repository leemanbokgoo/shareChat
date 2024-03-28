package shop.com.shareChat.domain.sharechat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.com.shareChat.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sharechat_sc")
@Entity
public class Sharechat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false )
    private LocalTime startTime;

    @Column(nullable = false )
    private LocalTime endTime;

    @Column(nullable = false )
    private LocalDate date;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int state;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Sharechat(Long id, LocalTime startTime, LocalTime endTime, LocalDate date, String content, int state, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.content = content;
        this.state = state;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
