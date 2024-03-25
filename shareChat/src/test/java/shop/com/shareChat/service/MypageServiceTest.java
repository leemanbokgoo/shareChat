package shop.com.shareChat.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.mypage.MypageSaveResDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MypageServiceTest extends DummyObject {
    @InjectMocks
    private MypageService mypageService;

    @Mock
    private MypageRepository mypageRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 마이페이지_성공_테스트코드() {

        //given

        User user = newUser("test");
        String title = "test입니다.";
        String job = "백엔드개발자";
        int career = 3;
        String intro = "안녕하세요";

        //stub
        MypageSaveReqDto reqDto = MypageSaveReqDto.builder()
                .title(title)
                .job(job)
                .career(career)
                .intro(intro)
                .build();

        //stub3
        Mypage mypage = newMockMypage(1L, title, job);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.save(any())).thenReturn(mypage);
        //when
        MypageSaveResDto saveResDto = mypageService.seveMypage(reqDto, "test@naver.com");
        Assertions.assertThat(saveResDto.getTitle()).isEqualTo(title);
        Assertions.assertThat(saveResDto.getJob()).isEqualTo(job);
        Assertions.assertThat(saveResDto.getIntro()).isEqualTo(intro);
    }

    @DisplayName("user가 null이면 마이페이지 등록은 실패한다.")
    @Test
    void 마이페이지_실패_테스트코드() {

        //given

        User user = null;
        String title = "test입니다.";
        String job = "백엔드개발자";
        int career = 3;
        String intro = "안녕하세요";

        //stub
        MypageSaveReqDto reqDto = MypageSaveReqDto.builder()
                .title(title)
                .job(job)
                .career(career)
                .intro(intro)
                .build();

        //stub3
        Mypage mypage = newMockMypage(1L, title, job);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        //when
        org.junit.jupiter.api.Assertions.assertThrows(CustomApiException.class, ()-> mypageService.seveMypage(reqDto, "test@naver.com"));
    }
}