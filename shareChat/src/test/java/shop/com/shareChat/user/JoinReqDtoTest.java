package shop.com.shareChat.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

class JoinReqDtoTest {

    @DisplayName("username은 한글,숫자,영문 허용, 1-10자리 숫자")
    @Test
    void username_성공_테스트() {
        String value = "닉네임1a";
        boolean result = Pattern.matches("^[a-zA-Z가-힣0-9]{1,10}$", value);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @DisplayName("username에는 특수기호가 들어갈수없다.")
    @Test
    void username_특수기호_실패테스트() {
        String value = "닉네임1a!@#$";
        boolean result = Pattern.matches("^[a-zA-Z가-힣0-9]{1,10}$", value);
        Assertions.assertThat(result).isEqualTo(false);
    }

    @DisplayName("username의 길이는 10자를 넘을 수 없다.")
    @Test
    void username_길이_실패테스트() {
        String value = "sdddddsdfasdfsdfddfd";
        boolean result = Pattern.matches("^[a-zA-Z가-힣0-9]{1,10}$", value);
        Assertions.assertThat(result).isEqualTo(false);
    }

    @Test
    void 이메일_성공_테스트() {
        String value = "test@naver.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", value);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @DisplayName("이메일에는 한글이 들어갈수 없다")
    @Test
    void 이메일_한글_실패테스트() {
        String value = "한글@naver.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", value);
        Assertions.assertThat(result).isEqualTo(false);
    }

    @DisplayName("이메일 양식을 맞춰주세요.")
    @Test
    void 이메일_양식_실패테스트() {
        String value = "한글123123sfdnaver.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", value);
        Assertions.assertThat(result).isEqualTo(false);
    }

    @DisplayName("이메일 양식을 맞춰주세요.")
    @Test
    void 이메일_양식_실패테스트2() {
        String value = "한글123123sfdnaversdfsdfom";
        boolean result = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", value);
        Assertions.assertThat(result).isEqualTo(false);
    }
}