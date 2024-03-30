package shop.com.shareChat.service.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.com.shareChat.domain.sharechat.ShareChatRepository;
import shop.com.shareChat.domain.sharechat.Sharechat;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.domain.userSharchat.UserSharechat;
import shop.com.shareChat.domain.userSharchat.UserSharechatRepository;
import shop.com.shareChat.dto.sharechat.ShareChatRepDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.com.shareChat.domain.userSharchat.QUserSharechat.userSharechat;

@ExtendWith(MockitoExtension.class)
class SharechatServiceImplTest extends DummyObject {

    @InjectMocks
    private SharechatServiceImpl sharechatService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSharechatRepository userSharechatRepository;

    @Mock
    private ShareChatRepository shareChatRepository;

    private User user;
    private User receiver;
    private Sharechat sharechat;

    @BeforeEach
    void set_up(){
        //gvein
        user = newMockUser(1L, "test");
        receiver = newMockUser(2L, "test2");
        sharechat = newMockShareChat("신청합니다.");
    }

    @DisplayName("신청받는 유저가 없는 경우 쉐어챗을 신청했을때 CustomApiException을 던지며 실패")
    @Test
    void saveWithoutReceiver(){

        //giwn
        LocalTime date = LocalTime.now();
        LocalDate day = LocalDate.now();
        ShareChatRepDto repDto = new ShareChatRepDto(date, date, day, "test입니다.");

        //when
        when(shareChatRepository.save(any(Sharechat.class))).thenReturn(sharechat);
        when(userRepository.findByUsername(receiver.getUsername())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.save(repDto, receiver.getUsername(), user.getId()));
    }

    @DisplayName("신청하는 유저의 데이터가 없는 경우 쉐어챗을 신청했을때 CustomApiException을 던지며 실패")
    @Test
    void saveWithoutUser(){

        //given
        LocalTime date = LocalTime.now();
        LocalDate day = LocalDate.now();
        ShareChatRepDto repDto = new ShareChatRepDto(date, date, day, "test입니다.");

        //when
        when(shareChatRepository.save(any(Sharechat.class))).thenReturn(sharechat);
        when(userRepository.findByUsername(receiver.getUsername())).thenReturn(Optional.ofNullable(receiver));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.save(repDto, receiver.getUsername(), user.getId()));
    }

    @DisplayName("userID가 없다면 getList는 CustomApiException를 던지며 실패한다.")
    @Test
    void getListWithoutUser(){

        //given
        LocalDate date = LocalDate.now();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.getList(user.getId(), DayOfWeek.FRIDAY, date));
    }

    @DisplayName("사용자가 없다면  CustomApiException를 던지며 실패한다.")
    @Test
    void getMyListWithoutUser(){
        //given
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        // then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.getMyList(user.getUsername(), 0));
    }

    @DisplayName("쉐어챗을 삭제할떄 해당 쉐어챗이 없다면 CustomApiException을 던지며 실패")
    @Test
    void deleteShareChatWithoutShareChat(){

        //when
        when(shareChatRepository.findById(sharechat.getId())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.deleteShareChat(sharechat.getId()));
    }

    @DisplayName("쉐어챗을 삭제할떄 해당 쉐어챗이 없다면 CustomApiException을 던지며 실패")
    @Test
    void deleteShareChatWithoutShareTIme(){

        //when
        when(shareChatRepository.findById(sharechat.getId())).thenReturn(Optional.of(sharechat));
        when(userSharechatRepository.findBySharechat(sharechat)).thenReturn(Optional.empty());
        //then
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.deleteShareChat(sharechat.getId()));
    }

    @DisplayName("쉐어챗 삭제 시 성공")
    @Test
    void deleteShareCha(){

        // given
        UserSharechat userSharechat = new UserSharechat();

        //when
        when(shareChatRepository.findById(sharechat.getId())).thenReturn(Optional.of(sharechat));
        when(userSharechatRepository.findBySharechat(sharechat)).thenReturn(Optional.of(userSharechat));

        assertDoesNotThrow(() -> {
            userSharechatRepository.delete(userSharechat);
        });

        assertDoesNotThrow(() -> {
            shareChatRepository.delete(sharechat);
        });

        //then
        sharechatService.deleteShareChat(sharechat.getId());

        verify(userSharechatRepository).findBySharechat(sharechat);
    }
}