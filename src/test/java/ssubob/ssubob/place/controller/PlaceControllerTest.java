package ssubob.ssubob.place.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.service.PlaceService;
import ssubob.ssubob.user.request.UserCreate;
import ssubob.ssubob.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Autowired
    protected PlaceRepository placeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void clean() {
        placeRepository.deleteAll();
    }

    @Test
    @DisplayName("일식을 요청했을때")
    @WithMockUser
    void test1() throws Exception {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        Place place2 = Place.builder()
                .title("은화수식당")
                .category("일식")
                .build();

        placeRepository.save(place);
        placeRepository.save(place2);

        mockMvc.perform(MockMvcRequestBuilders.get("/place/category/{category}", "일식")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("마루스시"))
                .andDo(MockMvcResultHandlers.print());
    }

}