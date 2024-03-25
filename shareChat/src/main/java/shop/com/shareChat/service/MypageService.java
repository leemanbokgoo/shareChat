package shop.com.shareChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.mypage.MypageSaveResDto;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;

    // 마이페이지 등록
    public MypageSaveResDto seveMypage(MypageSaveReqDto mypageSaveReqDto, User user){
        Mypage mypagePs = mypageRepository.save(mypageSaveReqDto.toEntity(user));
        return new MypageSaveResDto(mypagePs);
    }

    // 마이페이지 수정


    // 마이페이지 조회

    // 마이페이지 삭제
}
