package shop.com.shareChat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.service.serviceImpl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 스프링 관련 bean들이 하나도 없느 ㄴ환경
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 가짜 환경을 만들어서 넣어야함.
    @InjectMocks
    private UserServiceImpl userService;

    @Mock // 가짜로 띄워서 메모리에 띄워서 injectMocks에 주입해준다.
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입_테스트() {

        Long id = 1L;
        String nickname ="test";
        String email= "test@naver.com";
        String password = "1234";

        // given
        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();

        //  Optional 객체 return
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
//        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        // stub3
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User testUser = User.builder()
                .id(id)
                .nickname(nickname)
                .passwrod(encPassword)
                .email(email)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.USER)
                .build();

        when(userRepository.save(any())).thenReturn(testUser);

        //when
        JoinResDto joinResponseDto = userService.join(joinReqDto);

        // then

        org.assertj.core.api.Assertions.assertThat(joinResponseDto.getId()).isEqualTo(id);
        org.assertj.core.api.Assertions.assertThat(joinResponseDto.getNickname()).isEqualTo(nickname);
        org.assertj.core.api.Assertions.assertThat(joinResponseDto.getEmail()).isEqualTo(email);
    }

    @DisplayName("유저가 null 인 경우")
    @Test
    void 회원가입_실패테스트_테스트() {

        Long id = 1L;
        String nickname ="test";
        String email= "test@naver.com";
        String password = "1234";

        // given
        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();

        //  Optional 객체 return
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // stub3
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User testUser = null;
        when(userRepository.save(any())).thenReturn(testUser);

        //when
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.join(joinReqDto);
        });
    }
}