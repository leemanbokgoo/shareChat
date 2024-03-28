package shop.com.shareChat.service;

import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChatMyListResDto;
import shop.com.shareChat.dto.sharechat.ShareChatRepDto;
import shop.com.shareChat.dto.sharechat.ShareChatResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SharechatService {
    // 쉐어챗 등록
    ShareChatResDto save(ShareChatRepDto repDto, String receiverName, Long userId);

    // 쉐어챗 목록 조회
    List<Map<String, String>> getList(Long userId, DayOfWeek day, LocalDate date);

    // 내 쉐어챗 들고오기
    List<ShareChatMyListResDto> getMyList(String username, int state);

    // 쉐어챗 삭제
    boolean delteShareChat(Long shareChatId);

}
