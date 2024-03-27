package shop.com.shareChat.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.dto.user.UserListResDto;
import shop.com.shareChat.dto.user.UserResDto;
import shop.com.shareChat.dto.user.UserSearchReqDto;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    // 회원 존재 여부 확인
    Optional<User> findByUsername(String username);

}
