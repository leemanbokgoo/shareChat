package shop.com.shareChat.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.UserService;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto){
        // 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());

        if ( userOP.isPresent()) {
            throw new CustomApiException(ErrorCode.USER_EXIST);
        }

        // 패스워드 인코딩
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));
        // dto 응답
        return new JoinResDto(userPS);
    }
}
