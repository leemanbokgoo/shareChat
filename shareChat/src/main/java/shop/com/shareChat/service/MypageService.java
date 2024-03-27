package shop.com.shareChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.*;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;

import java.util.Optional;


public interface MypageService {

    MypageSaveResDto seve(MypageSaveReqDto mypageSaveReqDto, String email);
    MyPageUpdateResDto update(MypageUpdateReqDto resDto, String username);

    // 마이페이지 조회
    MypageResDto getMypage(Long userId);
}
