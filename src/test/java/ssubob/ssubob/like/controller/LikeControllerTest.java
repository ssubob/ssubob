package ssubob.ssubob.like.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ssubob.ssubob.like.domain.Like;
import ssubob.ssubob.like.repository.LikeRepository;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LikeControllerTest {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void clean() {
        likeRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("좋아요한 식당과의 좋아요 여부를 확인한다.")
    @WithMockUser(username = "test@test.com")
    @Transactional//?
    void get() throws Exception {
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        likeRepository.save(Like.builder()
                .place(place)
                .user(user)
                .build());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/like/get/{placeId}",place.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("좋아요하지 않은 식당과의 좋아요 여부를 확인한다.")
    @WithMockUser(username = "test@test.com")
    void getWhenFailed() throws Exception {
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/like/get/{placeId}",place.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("특정 식당에 좋아요를 설정한다.")
    @WithMockUser(username = "test@test.com")
    @Transactional
    void create() throws Exception {
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/like/create/{placeId}",place.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("특정 식당에 좋아요를 해제한다.")
    @WithMockUser(username = "test@test.com")
    void delete() throws Exception {
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        likeRepository.save(Like.builder()
                .place(place)
                .user(user)
                .build());

        //expect
        mockMvc.perform(MockMvcRequestBuilders.delete("/like/delete/{placeId}",place.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //안에 값 테스트하는거 작성해야함.
    }
}