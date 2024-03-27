package shop.com.shareChat.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserCustomRepositoryIml;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.user.*;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.UserService;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final UserCustomRepositoryIml userCustomRepositoryIml;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
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

    // 유저 목록 조회
    @Transactional(readOnly = true)
    public Page<UserListResDto> userList(Pageable pageable, UserSearchReqDto searchRequestDto) {
//        int page = pageable.getPageNumber() - 1; //  page 위치에 있는 값은 0부터 시작
//        int pageLimit = 5; // 한페이지에 보여줄 글 개수
        List<UserListResDto> content =  userCustomRepositoryIml.getSearchUser(pageable, searchRequestDto);
        Long count = userCustomRepositoryIml.getCount( pageable, searchRequestDto);
        return new PageImpl<>(content, pageable, count);
    }
}
