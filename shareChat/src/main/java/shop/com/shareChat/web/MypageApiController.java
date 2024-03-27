package shop.com.shareChat.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.aop.annotation.LoginCheck;
import shop.com.shareChat.confing.auth.LoginUser;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.mypage.*;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.MypageService;

@RequestMapping("/api/m")
@RequiredArgsConstructor
@RestController
public class MypageApiController {

    private final MypageService mypageService;
    private final HttpSession session;
    @PostMapping("/")
    @LoginCheck
    public ResponseEntity<?> join(@RequestBody @Valid MypageSaveReqDto saveReqDto, BindingResult bindingResult,@LoginUser SessionUser sessionUser) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }
        MypageSaveResDto joinRespDto = mypageService.seve(saveReqDto, sessionUser.getUsername());
        return new ResponseEntity<>(new HttpResponseDto<>(1, "마이페이지 등록 성공", joinRespDto), HttpStatus.CREATED);
    }

    // 수정
    @PutMapping("/update")
    @LoginCheck
    public ResponseEntity<?> update(@RequestBody @Valid MypageUpdateReqDto reqDto , BindingResult bindingResult,  @LoginUser SessionUser user){
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }

        MyPageUpdateResDto resDto = mypageService.update(reqDto, user.getUsername());
        return new ResponseEntity<>(new HttpResponseDto<>(1, "마이페이지 수정 성공", resDto), HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getMypage(@PathVariable Long userId){
        MypageResDto resDto = mypageService.getMypage(userId);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "마이페이지 등록 성공", resDto), HttpStatus.CREATED);

    }
}