package shop.com.shareChat.domain.shartime;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChaListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShareTimeCustomRepository{

    // 목록 조회
    List<ShareTimeListResDto> getList(User user);

    List<ShareChaListResDto> getTimeByDayforWeek(User user, DayOfWeek day);

}
