package shop.com.shareChat.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSearchReqDto {

    // 필터
    private boolean state;
    private String occupation;

    private String search;// 검색


    @Builder
    public UserSearchReqDto(boolean state, String occupation, String search) {
        this.state = state;
        this.occupation = occupation;
        this.search = search;
    }
}