package shop.com.shareChat.domain.user;

import org.springframework.data.domain.Pageable;
import shop.com.shareChat.dto.user.UserListResDto;
import shop.com.shareChat.dto.user.UserResDto;
import shop.com.shareChat.dto.user.UserSearchReqDto;

import java.util.List;

public interface UserCustomRepository {
    //     유저 목록 조회
    List<UserListResDto> getSearchUser(Pageable pageable, UserSearchReqDto searchRequestDto);
    // 페이징
    Long getCount(Pageable pageable, UserSearchReqDto searchRequestDto);
    // 유저 조회
    UserResDto getUser(Long userId);
}
