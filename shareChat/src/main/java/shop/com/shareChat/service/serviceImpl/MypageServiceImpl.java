package shop.com.shareChat.service.serviceImpl;

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
import shop.com.shareChat.service.MypageService;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {
    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;

    // 마이페이지 등록
    @Transactional
    public MypageSaveResDto seve(MypageSaveReqDto mypageSaveReqDto, String email){

        Optional<User> userOP = userRepository.findByUsername(email);

        if ( userOP.isEmpty()) {
            throw new CustomApiException(ErrorCode.USER_NOT_FOUND);
        }
        Mypage mypagePs = mypageRepository.save(mypageSaveReqDto.toEntity(userOP.get()));
        return new MypageSaveResDto(mypagePs);
    }

    // 마이페이지 수정
    @Transactional
    public MyPageUpdateResDto update(MypageUpdateReqDto resDto, String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new CustomApiException(ErrorCode.NOT_USER));

        Optional<Mypage> mypage = Optional.ofNullable(mypageRepository.findByUser(user));

        if( mypage.isEmpty() ){
            throw new CustomApiException(ErrorCode.MYPAGE_NOT_FOUND);
        }
        mypage.get().update(resDto);

        return new MyPageUpdateResDto(mypage.get());
    }

    //     user+ 마이페이지 조회
    @Transactional(readOnly = true)
    public MypageResDto getMypage(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        MypageResDto mypagePs =  Optional.ofNullable(mypageRepository.getMypage(user))
                .orElseThrow(() -> new CustomApiException(ErrorCode.MYPAGE_NOT_FOUND));
        return mypagePs;
    }

    // 마이페이지 쉐어챗 설정 onoff
    @Override
    public Boolean shareChatOnOff(String username, boolean state) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        Mypage mypagePs =  Optional.ofNullable(mypageRepository.findByUser(user))
                .orElseThrow(() -> new CustomApiException(ErrorCode.MYPAGE_NOT_FOUND));

        Mypage mypage= mypagePs.updateState(state);

        if ( mypage != null ){
            return true;
        } else {
            return false;
        }
    }
}
