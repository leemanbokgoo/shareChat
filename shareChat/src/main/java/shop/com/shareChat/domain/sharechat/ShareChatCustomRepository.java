package shop.com.shareChat.domain.sharechat;

import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChaListResDto;
import shop.com.shareChat.dto.sharechat.ShareChatMyListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ShareChatCustomRepository {
    List<ShareChaListResDto> getTimeByDayforWeek( Long userId, LocalDateTime date, int state);

    List<ShareChatMyListResDto> getMyList(User user, int state);
}
