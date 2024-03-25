package shop.com.shareChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.mypage.MypageSaveResDto;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;

    // 마이페이지 등록
    public MypageSaveResDto seveMypage(MypageSaveReqDto mypageSaveReqDto, String email){

        Optional<User> userOP = userRepository.findByEmail(email);

        if ( userOP.isEmpty()) {
            throw new CustomApiException(ErrorCode.USER_NOT_FOUND);
        }

        Mypage mypagePs = mypageRepository.save(mypageSaveReqDto.toEntity(userOP.get()));
        return new MypageSaveResDto(mypagePs);
    }

    // 마이페이지 수정


    // 마이페이지 조회

    // 마이페이지 삭제
}
