package shop.com.shareChat.ex;

public class CustomApiException extends RuntimeException{

    private ErrorCode errorCode;
    public CustomApiException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
