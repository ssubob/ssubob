package ssubob.ssubob.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;
import ssubob.ssubob.user.request.UserCreate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void create() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .password("abcdefg")
                .nickname("tom")
                .build();

        UserCreate userCreate2 = UserCreate.builder()
                .email("test2@test.com")
                .password("abcdefg")
                .nickname("tom2")
                .build();

        //when
        userService.create(userCreate);
        userService.create(userCreate2);

        //then
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 2);
    }
}