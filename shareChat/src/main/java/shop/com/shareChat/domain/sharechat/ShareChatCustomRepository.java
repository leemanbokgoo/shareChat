package shop.com.shareChat.domain.sharechat;

import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChaListResDto;
import shop.com.shareChat.dto.sharechat.ShareChatMyListResDto;

import java.time.LocalDate;
import java.util.List;

public interface ShareChatCustomRepository {
    List<ShareChaListResDto> getTimeByDayforWeek(Long userId, LocalDate date, int state);

    List<ShareChatMyListResDto> getMyList(User user, int state);

    List<ShareChaListResDto> getDayOfShareCht(User User, LocalDate date);
}
