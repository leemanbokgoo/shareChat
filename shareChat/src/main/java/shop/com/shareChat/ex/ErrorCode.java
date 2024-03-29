package shop.com.shareChat.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(400, "요청 값을 확인해주세요."),
    USER_EXIST(400, "동일한 사용자가 존재합니다."),
    VALIDATION_ERROR(402, "잘못된 요청입니다."),
    IMAGE_TYPE_ERROR(402, "이미지가 아닙ㄴ디ㅏ."),

    //404 NOT_FOUND 잘못된 리소스 접근
    USER_NOT_FOUND(404, "존재하지 않는 사용자 ID 입니다."),
    MYPAGE_NOT_FOUND(404, "존재하지 않는 마이페이지 입니다."),
    SHARECHAT_NOT_FOUND(404, "존재하지 않는 쉐어챗 입니다."),

    // 권한 관련
    NOT_USER(401, "로그인을 해주세요."),
    NOT_AUTHORIZATION(403, "권한이 없습니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버에러입니다. 개발자에게 문의해주세요."),
    FAIL_LOGIN(500, "로그인에 실패했습니다"),
    DLELETE_FAIL_EEOR(500, "이미지 삭제되지않았습니다."),
    UPLOAD_FAIL_EEROR(500, "이미지 수정이 실패했습니다.")
    ;
    private final int status;
    private final String message;
}