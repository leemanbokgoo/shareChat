package shop.com.shareChat.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.UserService;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/session/test")
    public String sessionTest(HttpSession httpSession){
        httpSession.setAttribute("name","user");
        return "name";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }

        JoinResDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }


}
