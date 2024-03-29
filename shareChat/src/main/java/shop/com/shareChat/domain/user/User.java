package shop.com.shareChat.domain.user;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_sc")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 20)
    private String username;

    @Column(nullable = false , length = 60)
    private String passwrod;

    @Column(nullable = false , length = 20)
    private String nickname;

    @Column
    private String profileImg;

    @Column(nullable = false)
    private int state;

    @Column(nullable = false)
    private Role role;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public User(Long id, String nickname, String passwrod, String username, Role role, int state, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.passwrod = passwrod;
        this.username = username;
        this.state = state;
        this.role = role;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public User update( String nickname ) {
        this.nickname = nickname;
        return this;
    }

    public User updateProfileImg( String profileImg){
        this.profileImg = profileImg;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
