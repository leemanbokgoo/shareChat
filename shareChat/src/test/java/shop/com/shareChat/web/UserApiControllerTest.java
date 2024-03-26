package shop.com.shareChat.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dummy.DummyObject;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserApiControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void 회원가입_성공_테스트() throws Exception {
        // given
        String nickname = "test";
        String password = "test123**";
        String username = "test123@naver.com";

        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .password(password)
                .username(username)
                .build();

        String requestBody = om.writeValueAsString(joinReqDto);

        mvc
                .perform(MockMvcRequestBuilders.post("/api/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void 회원가입_이메일실패_테스트() throws Exception {
        // given
        String nickname = "test";
        String password = "test123**";
        String username = "test123";

        JoinReqDto joinReqDto = JoinReqDto.builder()
                .nickname(nickname)
                .password(password)
                .username(username)
                .build();

        String requestBody = om.writeValueAsString(joinReqDto);

         mvc
                .perform(MockMvcRequestBuilders.post("/api/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}