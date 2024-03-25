package shop.com.shareChat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HttpResponseDto <T> {
    private final Integer code; // 1성공 -1 실패
    private final String msg;
    private final T data;

}
