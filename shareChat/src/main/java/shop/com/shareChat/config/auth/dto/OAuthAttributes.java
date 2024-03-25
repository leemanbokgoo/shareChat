package shop.com.shareChat.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    // checkpoint 임의의 비밀번호
    private String password;
    private String nickname;
    private GrantedAuthority authority;
    // 나중에 이미지 추가
//    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname,String password, String email ) {
        this.attributes = attributes;
        this.password = password;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다.
    public static OAuthAttributes ofSocialLogin(String registrationId, String userNAmeAttributeName, Map<String, Object> attributes) {
        // 사용자 로그인이 네이버인 경우
        if ("naver".equals(registrationId)) return ofNaver("id", attributes);
        // 사용자 로그인인 구글인경우
        return ofGoogle(userNAmeAttributeName, attributes);
    }

    // google login OAuth
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("name"))
                .password((String) attributes.get("email"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    // naver login OAuth
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .nickname((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 회원가입
    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .passwrod(password)
                .email(email)
                .role(Role.USER)
                .build();
    }
}