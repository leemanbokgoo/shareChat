package shop.com.shareChat.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;
import shop.com.shareChat.domain.mypage.Mypage;
import shop.com.shareChat.domain.mypage.MypageRepository;
import shop.com.shareChat.domain.shartime.ShareTimeRepository;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageResDto;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShareTimeServiceImplTest extends DummyObject {

    @InjectMocks
    private ShareTimeServiceImpl shareTimeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShareTimeRepository shareTimeRepository;

    private User user;

    @BeforeEach
    void set_up() {
        user = newUser("test");
    }

    @DisplayName("존재하지않는 사용자의 시간설정을 요청한 경우 CustomApiException 을 던지며 실패")
    @Test
    void sharTimeWithoutUser() {
        // given
        LocalTime startTIme = LocalTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY,startTIme, startTIme,30, user);

        //when
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        //then
        assertThrows(CustomApiException.class, ()-> shareTimeService.save(reqDto, user.getUsername()));
        verify(userRepository).findByUsername(any());
    }

    @DisplayName("사용자 시간 설정 등록")
    @Test
    void shareTimeoffOn() {
        // given
        User targetUser = newUser("test");
        LocalTime startTime = LocalTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY, startTime, startTime, 30, targetUser);
        //when
        when(userRepository.findByUsername(targetUser.getUsername())).thenReturn(Optional.ofNullable(targetUser));
        when(shareTimeRepository.save(any(Sharetime.class))).thenReturn(newMockShareTime());

        //then
        shareTimeService.save(reqDto, targetUser.getUsername());
        verify(userRepository).findByUsername(targetUser.getUsername());
        verify(shareTimeRepository).save(any(Sharetime.class));
    }

}