package shop.com.shareChat.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.service.serviceImpl.UserServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입_테스트() {

        // given
        Long id = 1L;
        String nickname ="test";
        String username= "test@naver.com";
        String password = "1234";
        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .username(username)
                .password(password)
                .build();

        //  Optional 객체 return
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        User testUser = newMockUser(id, nickname);
        when(userRepository.save(any())).thenReturn(testUser);

        //when
        JoinResDto joinResponseDto = userService.join(joinReqDto);

        // then
        Assertions.assertThat(joinResponseDto.getId()).isEqualTo(id);
        Assertions.assertThat(joinResponseDto.getNickname()).isEqualTo(nickname);
        Assertions.assertThat(joinResponseDto.getUsername()).isEqualTo(username);
    }

    @DisplayName("유저가 null 인 경우")
    @Test
    void 회원가입_실패테스트_테스트() {

        Long id = 1L;
        String nickname ="test";
        String username= "test@naver.com";
        String password = "1234";

        // given
        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .username(username)
                .password(password)
                .build();

        //  Optional 객체 return
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub3
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User testUser = null;
        when(userRepository.save(any())).thenReturn(testUser);

        //when
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            userService.join(joinReqDto);
        });
    }
}