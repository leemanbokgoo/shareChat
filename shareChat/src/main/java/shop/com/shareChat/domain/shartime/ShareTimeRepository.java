package shop.com.shareChat.domain.shartime;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ShareTimeRepository extends JpaRepository<Sharetime, Long>, ShareTimeCustomRepository{
    List<Sharetime> findByUser(User user);
    int findShareChatTimeById(Long id);


}
