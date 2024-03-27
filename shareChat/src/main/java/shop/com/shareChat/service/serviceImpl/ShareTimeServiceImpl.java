package shop.com.shareChat.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.shartime.ShareTimeRepository;
import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.sharetime.*;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.ShareTimeService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShareTimeServiceImpl implements ShareTimeService {
    private final ShareTimeRepository shareTimeRepository;
    private final UserRepository userRepository;

    // 등록
    @Transactional
    @Override
    public ShareTimeResDto save(ShareTimeReqDto reqDto, String username) {
        User user = (userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND)));
        Sharetime sharetimePS = shareTimeRepository.save(reqDto.toEntity(user));

        return new ShareTimeResDto(sharetimePS);
    }

    // 쉐어챗 시간 설정 수정
    // 삭제 후 등록
    @Override
    public boolean update(ShareTimeReqDto reqDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        List<Sharetime> sharetimeList = shareTimeRepository.findByUser(user);

        // 기존 설정이 있다면 삭제
        if (!sharetimeList.isEmpty()){
            for ( Sharetime sharetime : sharetimeList){
                shareTimeRepository.delete(sharetime);
            }
        }
        // 기존 설정 등록
        shareTimeRepository.save(reqDto.toEntity(user));
        return true;
    }

    @Override
    public List<ShareTimeListResDto> getList(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        return shareTimeRepository.getList(user);
    }


}
