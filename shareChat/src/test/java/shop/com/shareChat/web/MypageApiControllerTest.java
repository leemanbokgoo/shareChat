package shop.com.shareChat.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dummy.DummyObject;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MypageApiControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager em;// 영속성 초기화

    @BeforeEach
    public void set_up(){
        userRepository.save(newUser("test"));
        em.clear();
    }

    @Test
    @WithMockUser(username = "test")
    public void 회원가입_성공_테스트() throws Exception {
        // given
        String title = "test";
        String job = "백엔드개발자";
        String intro = "test123@naver.com";
        int career = 3;

        MypageSaveReqDto joinReqDto = MypageSaveReqDto.builder()
                .title(title)
                .job(job)
                .intro(intro)
                .career(career)
                .build();

        String requestBody = om.writeValueAsString(joinReqDto);
        MockHttpSession session  = new MockHttpSession();

        // 세션에 필요한 속성 설정
        session.setAttribute("user", new SessionUser(newUser("test")));

        mvc
                .perform(MockMvcRequestBuilders.post("/api/m/save")
                        .session(session)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}