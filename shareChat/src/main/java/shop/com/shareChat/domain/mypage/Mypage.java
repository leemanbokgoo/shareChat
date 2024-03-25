package shop.com.shareChat.domain.mypage;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mypage_sc")
@Entity
public class Mypage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 40)
    private String title;

    @Column(nullable = false)
    private int career;

    @Column(nullable = false , length = 20)
    private String job;

    @Column(nullable = false, length = 1000)
    private String intro;

    @Column(nullable = false)
    private boolean state;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Mypage(Long id, String title, int career, String job, String intro, boolean state, User user, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.career = career;
        this.job = job;
        this.intro = intro;
        this.state = state;
        this.user = user;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}