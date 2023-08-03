package ssubob.ssubob.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;
import ssubob.ssubob.user.request.UserCreate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입에 성공한다")
    void create() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .password("abcdefg")
                .nickname("tom")
                .build();

        String requestBody = objectMapper.writeValueAsString(userCreate);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.com"));
    }

    @Test
    @DisplayName("이미 존재하는 이메일이라서 회원가입에 실패한다")
    void createWhenFailedByIdDuplication() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .password("abcdefg")
                .nickname("tom")
                .build();

        String requestBody = objectMapper.writeValueAsString(userCreate);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userCreate.getEmail()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("가입하려는 데이터가 비어있어서 회원가입에 실패한다.")
    void createWhenFailedByEmptyData() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email(" ")
                .password(" ")
                .nickname(" ")
                .build();

        String requestBody = objectMapper.writeValueAsString(userCreate);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}