package shop.com.shareChat.service;

import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;


public interface UserService {
    public JoinResDto join(JoinReqDto joinReqDto);
}
