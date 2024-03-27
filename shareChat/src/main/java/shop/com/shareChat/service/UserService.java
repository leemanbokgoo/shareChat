package shop.com.shareChat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.dto.user.UserListResDto;
import shop.com.shareChat.dto.user.UserSearchReqDto;

import java.util.List;


public interface UserService {
    public JoinResDto join(JoinReqDto joinReqDto);

    // 유저 목록
    Page<UserListResDto> userList(Pageable pageable, UserSearchReqDto searchRequestDto);

}
