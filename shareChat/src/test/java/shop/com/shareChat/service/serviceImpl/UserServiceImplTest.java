package shop.com.shareChat.service.serviceImpl;

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
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends DummyObject {
    // 가짜 환경을 만들어서 넣어야함.
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입_테스트() {

        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("test");
        joinReqDto.setUsername("test@naver.com");
        String encodePassword = passwordEncoder.encode("1234");
        joinReqDto.setPassword(encodePassword);

        // stub 이라는 처리가 필요함. -> 가정법, 가설 같은 것
        // 만약에 무슨 변수라도 들어오면 비어있는 Optional 객체가 return 된다.
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub3
        User user = newMockUser(1L, "test");
        when(userRepository.save(any())).thenReturn(user);

        //when
        JoinResDto joinResponseDto = userService.join(joinReqDto);
        // then

        org.assertj.core.api.Assertions.assertThat(joinResponseDto.getId()).isEqualTo(1L);
        org.assertj.core.api.Assertions.assertThat(joinResponseDto.getUsername()).isEqualTo("test@naver.com");
        Assertions.assertThat(joinResponseDto.getNickname()).isEqualTo("test");
    }

    @DisplayName("req가 null이면 에러가 뜬다.")
    @Test
    void 회원가입_실패_테스트() {

        // given
        JoinReqDto joinReqDto = null;
        //when
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, ()->userService.join(joinReqDto));

    }
}