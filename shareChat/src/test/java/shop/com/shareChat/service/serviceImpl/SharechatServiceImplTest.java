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
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.domain.userSharchat.UserSharechatRepository;
import shop.com.shareChat.dto.sharechat.ShareChatRepDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.ex.CustomApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        LocalTime date = LocalTime.now();
        LocalDate day = LocalDate.now();
        ShareChatRepDto repDto = new ShareChatRepDto(date, date, day, "test입니다.");
        when(shareChatRepository.save(any(Sharechat.class))).thenReturn(sharechat);

        when(userRepository.findByUsername(receiver.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.save(repDto, receiver.getUsername(), user.getId()));
    }

    @DisplayName("신청하는 유저의 데이터가 없는 경우 쉐어챗을 신청했을때 CustomApiException을 던지며 실패")
    @Test
    void saveWithoutUser(){

        LocalTime date = LocalTime.now();
        LocalDate day = LocalDate.now();
        ShareChatRepDto repDto = new ShareChatRepDto(date, date, day, "test입니다.");
        when(shareChatRepository.save(any(Sharechat.class))).thenReturn(sharechat);

        when(userRepository.findByUsername(receiver.getUsername())).thenReturn(Optional.ofNullable(receiver));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomApiException.class, () -> sharechatService.save(repDto, receiver.getUsername(), user.getId()));
    }

}