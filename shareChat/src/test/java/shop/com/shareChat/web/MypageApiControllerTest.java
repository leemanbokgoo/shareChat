package shop.com.shareChat.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.confing.auth.LoginUserArgumentResolver;
import shop.com.shareChat.confing.auth.dto.SessionUser;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.dto.mypage.MypageSaveReqDto;
import shop.com.shareChat.dto.mypage.MypageSaveResDto;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dummy.DummyObject;
import shop.com.shareChat.service.MypageService;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @MockBean
    MypageService mypageService;

    @MockBean
    LoginUserArgumentResolver loginUserArgumentResolver;

    @BeforeEach
    public void set_up() throws Exception {
        userRepository.save(newUser("test"));
        doReturn(true).when(loginUserArgumentResolver).resolveArgument(any(), any(), any(), any());
        em.clear();
    }

    MockHttpSession mockHttpSession;

    @Test
    @WithMockUser(username = "test")
    public void 마이페이지_등록_테스트() throws Exception {
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
        mockHttpSession = new MockHttpSession();

        // 세션에 필요한 속성 설정
        mockHttpSession.setAttribute("user", new SessionUser(newUser("test")));

        //when
        mvc
                .perform(MockMvcRequestBuilders.post("/api/m/")
                        .session(mockHttpSession)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}