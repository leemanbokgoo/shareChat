package shop.com.shareChat.domain.sharechat;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.domain.user.User;

import java.util.Optional;

public interface ShareChatRepository extends JpaRepository<Sharechat, Long>, ShareChatCustomRepository {
}
