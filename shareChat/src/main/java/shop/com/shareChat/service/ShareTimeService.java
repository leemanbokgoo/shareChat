package shop.com.shareChat.service;

import shop.com.shareChat.domain.shartime.Sharetime;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;
import shop.com.shareChat.dto.sharetime.ShareTimeResDto;

import java.util.List;

public interface ShareTimeService {

    // 쉐어챗 설정 등록
    ShareTimeResDto save(ShareTimeReqDto reqDto, String username);

    // 쉐어챗 설정 시간 수정
    boolean update(ShareTimeReqDto reqDto, String username);

    // 시간 목록 조회
    List<ShareTimeListResDto> getList(Long userId);

}
