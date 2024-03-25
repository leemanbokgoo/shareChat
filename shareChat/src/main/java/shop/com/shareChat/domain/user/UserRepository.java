package shop.com.shareChat.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.dto.user.JoinReqDto;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // 회원 존재 여부 확인
    Optional<User> findByEmail(String email);
}
