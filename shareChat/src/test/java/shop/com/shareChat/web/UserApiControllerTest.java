package shop.com.shareChat.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.dto.user.JoinReqDto;
import shop.com.shareChat.dto.user.JoinResDto;
import shop.com.shareChat.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserApiControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @Autowired
    ObjectMapper om;


    @DisplayName("유저 정보를 어쩌고한다.")
    @Test
    void userJoin() throws Exception {

        //given
        JoinReqDto reqDto = new JoinReqDto();
        reqDto.setNickname("test");
        reqDto.setPassword("1234");
        reqDto.setUsername("test@naver.com");
        String requestBody = om.writeValueAsString(reqDto);

        // when, then
        when( userService.join(any())).thenReturn(any(JoinResDto.class));
        mvc
                .perform(MockMvcRequestBuilders.post("/api/user/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }


}