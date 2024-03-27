package shop.com.shareChat.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.aop.annotation.LoginCheck;
import shop.com.shareChat.confing.auth.LoginUser;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.sharechat.ShareChatMyListResDto;
import shop.com.shareChat.dto.sharechat.ShareChatRepDto;
import shop.com.shareChat.dto.sharechat.ShareChatResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeReqDto;
import shop.com.shareChat.dto.sharetime.ShareTimeResDto;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.ShareTimeService;
import shop.com.shareChat.service.SharechatService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ShareChatController {
    private final SharechatService sharechatService;

    // 등록
    @PostMapping("/")
    @LoginCheck
    public ResponseEntity<?> join(@RequestBody @Valid ShareChatRepDto saveReqDto, @PathVariable Long userId, BindingResult bindingResult, @LoginUser SessionUser sessionUser) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }
        ShareChatResDto resDto = sharechatService.save(saveReqDto, sessionUser.getUsername(),userId);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "쉐어챗 등록 성공", resDto), HttpStatus.CREATED);
    }


    // 쉐어챗 신청 시간 조회
    @GetMapping("/list/{userId}")
    @LoginCheck
    public ResponseEntity<?> getList(@PathVariable Long userId, @RequestParam LocalDateTime date, @RequestParam int state){
        List<Map<String, String>> list = sharechatService.getList(userId, date, state);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "쉐어챗 시간 목록 조회 성공", list), HttpStatus.CREATED);
    }

    @GetMapping("/list/m/{state}")
    @LoginCheck
    public ResponseEntity<?> getMyList(@PathVariable int state, @LoginUser SessionUser user){
        List<ShareChatMyListResDto> list = sharechatService.getMyList( user.getUsername(), state);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "내 쉐어챗 시간 목록 조회 성공", list), HttpStatus.CREATED);
    }

    @DeleteMapping("/{shareChatId}")
    @LoginCheck
    public ResponseEntity<?> deleteShareChat(@PathVariable Long shareChatId, @LoginUser SessionUser user){
        boolean result = sharechatService.delteShareChat(shareChatId);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "내 쉐어챗 시간 삭제", result), HttpStatus.CREATED);
    }
}
