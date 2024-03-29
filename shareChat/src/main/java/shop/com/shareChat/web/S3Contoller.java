package shop.com.shareChat.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.com.shareChat.aop.annotation.LoginCheck;
import shop.com.shareChat.confing.auth.LoginUser;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.UserService;
import shop.com.shareChat.service.serviceImpl.S3ImageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/S3")
public class S3Contoller {

    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;

    @PostMapping("/profileImg/upload")
    @LoginCheck
    public ResponseEntity<?> profileS3Upload(@RequestParam("image") MultipartFile image,  @LoginUser SessionUser user) throws Exception {
        String profileImage = s3ImageService.upload(image);
        HttpHeaders httpHeaders = new HttpHeaders();

        // 올린 이미지 주소 db 저장
        User userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new CustomApiException(ErrorCode.NOT_USER));

        userEntity.updateProfileImg(profileImage);

        return new ResponseEntity<>(new HttpResponseDto<>(1, "이미지등록 성공", profileImage), HttpStatus.CREATED);

    }

    @GetMapping("/profileImg/delete")
    @LoginCheck
    public ResponseEntity<?> s3delete(@RequestParam String imgAddr,@LoginUser SessionUser user) throws Exception {
        s3ImageService.deleteImageFromS3(imgAddr);
        HttpHeaders httpHeaders = new HttpHeaders();

        // db 삭제
        User userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new CustomApiException(ErrorCode.NOT_USER));
        userEntity.updateProfileImg(null);

        return new ResponseEntity<>(new HttpResponseDto<>(1, "이미지삭제 성공", userEntity.getId()), HttpStatus.CREATED);
    }
}
