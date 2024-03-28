package shop.com.shareChat.domain.userSharchat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.domain.sharechat.ShareChatCustomRepository;
import shop.com.shareChat.domain.sharechat.Sharechat;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSharechatRepository extends JpaRepository<UserSharechat,Long> {
    Optional<UserSharechat> findBySharechat(Sharechat sharechat);

}
