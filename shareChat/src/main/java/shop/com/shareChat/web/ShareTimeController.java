package shop.com.shareChat.web;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.aop.annotation.LoginCheck;
import shop.com.shareChat.confing.auth.LoginUser;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.mypage.MyPageUpdateResDto;
import shop.com.shareChat.dto.mypage.MypageUpdateReqDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;
import shop.com.shareChat.dto.sharetime.ShareTimeResDto;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.ShareTimeService;

import java.util.List;

@RequestMapping("/api/sct")
@RequiredArgsConstructor
@RestController
public class ShareTimeController {

    private final ShareTimeService shareTimeService;

    // 등록
    @PostMapping("/")
    @LoginCheck
    public ResponseEntity<?> join(@RequestBody @Valid ShareTimeReqDto saveReqDto, BindingResult bindingResult, @LoginUser SessionUser sessionUser) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }
        ShareTimeResDto joinRespDto = shareTimeService.save(saveReqDto, sessionUser.getUsername());
        return new ResponseEntity<>(new HttpResponseDto<>(1, "쉐어챗 시간설정 성공", joinRespDto), HttpStatus.CREATED);
    }


    //수정
    @PutMapping("/update")
    @LoginCheck
    public ResponseEntity<?> update(@RequestBody @Valid ShareTimeReqDto reqDto , BindingResult bindingResult, @LoginUser SessionUser user){
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }

        boolean result = shareTimeService.update(reqDto, user.getUsername());
        return new ResponseEntity<>(new HttpResponseDto<>(1, "쉐어챗 수정 성공", result), HttpStatus.CREATED);
    }

    // 쉐어챗 시간 설정 조회
    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getList(@PathVariable Long userId){
        List<ShareTimeListResDto> list = shareTimeService.getList(userId);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "쉐어챗 목록 조회 성공", list), HttpStatus.CREATED);
    }
}
