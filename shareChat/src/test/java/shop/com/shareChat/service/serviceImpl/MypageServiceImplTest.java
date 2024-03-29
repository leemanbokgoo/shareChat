package shop.com.shareChat.service.serviceImpl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageResDto;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.mypage.MypageSaveResDto;
import shop.com.shareChat.dto.mypage.MypageUpdateReqDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.util.Optional;

import static org.joda.time.PeriodType.time;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MypageServiceImplTest extends DummyObject {

    @InjectMocks
    private MypageServiceImpl mypageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MypageRepository mypageRepository;

    private User user;

    @BeforeEach
    void set_up(){
        //gvein
        user = newUser("test");
        userRepository.save(user);
    }

    @AfterEach
    void after(){
        userRepository.deleteAll();
    }

    @DisplayName("존재하지않는 사용자의 마이페이지 등록을 요청한 경우 CustomApiException 을 던지며 실패")
    @Test
    void mypageWithoutUser() {
        // given
        MypageSaveReqDto reqDto = new MypageSaveReqDto();
        Mypage mypage = new Mypage();
        reqDto.setJob("test");
        reqDto.setIntro("testestA");
        reqDto.setCareer(3);
        reqDto.setTitle("title");
        reqDto.setOccupation(1);

        //when
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        //then
        assertThrows(CustomApiException.class, ()-> mypageService.save(reqDto, user.getUsername()));
        verify(mypageRepository, times(0)).save(mypage);
    }

    @DisplayName("사용자인 경우 마이페이지 등록")
    @Test
    void mypageWithSuccess() {
        // given
        MypageSaveReqDto reqDto = new MypageSaveReqDto();
        String title = "title";
        reqDto.setJob("test");
        reqDto.setIntro("testestA");
        reqDto.setCareer(3);
        reqDto.setTitle(title);
        reqDto.setOccupation(1);
        Mypage mypage = newMockMypage(1L, "title", "백엔드개발자");

        //when
        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.save(any())).thenReturn(mypage);
        MypageSaveResDto resDto = mypageService.save(reqDto, user.getUsername());

        //then
        org.assertj.core.api.Assertions.assertThat(resDto.getJob()).isEqualTo("백엔드개발자");
        org.assertj.core.api.Assertions.assertThat(resDto.getTitle()).isEqualTo("title");
    }

    @DisplayName("존재하지않는 사용자의 마이페이지 수정 요청한 경우 CustomApiException 을 던지며 실패")
    @Test
    void mypageUpdateWithoutUser() {
        // given
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //then
        assertThrows(CustomApiException.class, ()-> mypageService.update(any(), user.getUsername()));
        verify(mypageRepository, times(0)).save(any());
    }

    @DisplayName("마이페이지가 없는 사용자가 수정 요청한 경우 CustomApiException 을 던지며 실패")
    @Test
    void mypageUpdateNotUser() {

        User mockUser = newMockUser(2L, "teset2");
        // given
        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.findByUser(user)).thenReturn(null);
        //then
        assertThrows(CustomApiException.class, ()-> mypageService.update(any(), mockUser.getUsername()));
        verify(mypageRepository, times(0)).save(any());
    }


}