package shop.com.shareChat.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.dto.user.UserListResDto;
import shop.com.shareChat.dto.user.UserSearchReqDto;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.MypageService;
import shop.com.shareChat.service.UserService;

import java.util.List;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }
        JoinResDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public Page<UserListResDto> getList(@PageableDefault(page = 1) Pageable pageable, @RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "search", required = false) String search ) {
        return userService.userList(pageable, new UserSearchReqDto(true , filter, search));
    }

}
