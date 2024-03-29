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
    private Mypage mypage;

    @BeforeEach
    void set_up(){
        //gvein
        user = newUser("test");
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

    @DisplayName("존재하는 게시물의 유저 아이디를 통해 게시물을 조회")
    @Test
    public void getMypage(){

        // given
        mypage = newMockMypage(1L, "test", "백엔드개발자");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        MypageResDto resDto = new MypageResDto(mypage.getId(),mypage.getJob(), mypage.getTitle(), mypage.getIntro(), mypage.getOccupation());
        when(mypageRepository.getMypage(user)).thenReturn(resDto);

        // when
        MypageResDto targetMypage = mypageService.getMypage(user.getId());

        // then
        assertEquals(targetMypage, resDto);
        verify(mypageRepository).getMypage(user);
    }


    @DisplayName("존재하지않는 게시물의 유저 아이디를 통해 게시물을 조회")
    @Test
    public void getMypageWithoutMypage(){
        // given
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.getMypage(user)).thenReturn(null);
        // when
        assertThrows(CustomApiException.class, ()-> mypageService.getMypage(user.getId()));

        // then
        verify(userRepository).findById(user.getId());
        verify(mypageRepository).getMypage(user);
    }

    @DisplayName("존재하지않는 유저 아이디를 통해 게시물을 조회")
    @Test
    public void getMypageWithoutUser(){
        // given
        mypage = newMockMypage(1L, "test", "백엔드개발자");
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        // when
        assertThrows(CustomApiException.class, ()-> mypageService.getMypage(user.getId()));

        //then
        verify(userRepository).findById(user.getId());
    }

    @DisplayName("쉐어챗의 상태값 변경시 유저가 없으면 CustomApiException을 던지며 실패.")
    @Test
    public void shareChatonOffWithoutUser(){
        // given
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        //when
        Assertions.assertThrows(CustomApiException.class, () -> mypageService.shareChatOnOff(user.getUsername(), true));
    }

    @DisplayName("쉐어챗의 상태값 변경시 마이페이지가 없으면 CustomApiException을 던지며 실패.")
    @Test
    public void shareChatonOffWithoutMypage(){
        // given
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.findByUser(user)).thenReturn(null);
        //when
        Assertions.assertThrows(CustomApiException.class, () -> mypageService.shareChatOnOff(user.getUsername(), true));
    }


    @DisplayName("쉐어챗의 상태값 변경시 성공.")
    @Test
    public void shareChatOnOff(){
        // given
        Mypage targetMypage = newMockMypage(1L, "title", "백엔드개발자");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(mypageRepository.findByUser(user)).thenReturn(targetMypage);
        //when
        mypageService.shareChatOnOff(user.getUsername(), true);

        // then
        verify(userRepository).findByUsername(user.getUsername());
        verify(mypageRepository).findByUser(user);
    }


}