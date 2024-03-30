package shop.com.shareChat.service.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.com.shareChat.domain.shartime.ShareTimeRepository;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.time.LocalDateTime;
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
        LocalTime date = LocalTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY,date, date,30, user);

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
        LocalTime date = LocalTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY, date, date, 30, targetUser);
        //when
        when(userRepository.findByUsername(targetUser.getUsername())).thenReturn(Optional.ofNullable(targetUser));
        when(shareTimeRepository.save(any(Sharetime.class))).thenReturn(newMockShareTime());

        //then
        shareTimeService.save(reqDto, targetUser.getUsername());
        verify(userRepository).findByUsername(targetUser.getUsername());
        verify(shareTimeRepository).save(any(Sharetime.class));
    }

    @DisplayName("존재하지않는 사용자의 시간설정을 수정하는 경우 CustomApiException 을 던지며 실패")
    @Test
    void updateShareTimeWithoutUser() {
        // given
        LocalTime date = LocalTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY,date, date,30, user);

        //when
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        //then
        assertThrows(CustomApiException.class, ()-> shareTimeService.update(reqDto, user.getUsername()));
        verify(userRepository).findByUsername(any());
    }

    @DisplayName("쉐어챗 시간 설정 수정 성공")
    @Test
    void updateHsareTimeSuccess(){

        //given
        LocalTime date = LocalTime.now();
        LocalDateTime datetime = LocalDateTime.now();
        ShareTimeReqDto reqDto = new ShareTimeReqDto(DayOfWeek.FRIDAY,date, date,30, user);

        //when
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        assertDoesNotThrow(() -> {
            shareTimeService.update(reqDto, user.getUsername());
        });

        verify(userRepository).findByUsername(user.getUsername());
    }

    @DisplayName("쉐어챗 시간 설정 조회시 사용자가 없는 경우 CustomApiException을 던지며 실패")
    @Test
    void getListWithoutUser(){
        //given
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        // when
        Assertions.assertThrows(CustomApiException.class, () -> shareTimeService.getList(user.getId()));
    }


    @DisplayName("쉐어챗 시간 설정 조회시 사용자가 없는 경우 CustomApiException을 던지며 실패")
    @Test
    void getListSuccess(){
        //given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // when
        shareTimeService.getList(user.getId());
        //then
        verify(shareTimeRepository).getList(user);
    }

}