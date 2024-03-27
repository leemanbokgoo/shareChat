package shop.com.shareChat.domain.mypage;

import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.mypage.MypageResDto;

public interface MypageCustomRepository {

    // 마이페이지 조회
    MypageResDto getMypage(User user);
}
