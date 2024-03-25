package shop.com.shareChat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.ex.ErrorCode;

public class CustomHttpHeaderUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpHeaderUtil.class);

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            HttpResponseDto<?> responseDto = new HttpResponseDto<>(1, "로그인성공", dto);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error( e.getMessage());
        }

    }

    public static void fail(HttpServletResponse response, ErrorCode errorCode, HttpStatus httpStatus) {
        try {
            ObjectMapper om = new ObjectMapper();
            HttpResponseDto<?> responseDto = new HttpResponseDto<>(-1, errorCode.getMessage(), null);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}