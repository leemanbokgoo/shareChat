package shop.com.shareChat.domain.mypage;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.domain.user.User;

import java.util.Optional;

public interface MypageRepository extends JpaRepository<Mypage, Long >, MypageCustomRepository {

    Mypage findByUser( User user);
}
