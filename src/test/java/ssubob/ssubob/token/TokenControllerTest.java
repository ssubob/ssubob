package ssubob.ssubob.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TokenControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인에 성공한다")
    void login() throws Exception {
        //given
        String email = "test@test.com";
        String password = "abcdefg";
        String nickname = "tom";
        User user = User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname("tom")
                .build();
        userRepository.save(user);

        //when & then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(email, password)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get("/place/한식")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("유효하지 않은 아이디와 비밀번호로 로그인 하는 경우 실패한다")
    void loginWhenFailed() throws Exception {
        //given
        String email = "test@test.com";
        String password = "abcdefg";

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(email, password)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("권한이 없는 사용자가 게시글 데이터에 접근하는 경우 실패한다")
    void accessDataWhenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/place/한식"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}