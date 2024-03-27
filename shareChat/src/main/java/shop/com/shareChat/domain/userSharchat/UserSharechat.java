package shop.com.shareChat.domain.userSharchat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.com.shareChat.domain.sharechat.Sharechat;
import shop.com.shareChat.domain.user.User;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "userShareChat_sc")
@Entity
public class UserSharechat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shareChat_id")
    private Sharechat sharechat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId")
    private User receiver;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserSharechat(Long id, Sharechat sharechat, User user, User receiver, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.sharechat = sharechat;
        this.user = user;
        this.receiver = receiver;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
