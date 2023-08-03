package ssubob.ssubob.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.comment.service.CommentService;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
        placeRepository.deleteAll();
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    @WithMockUser
    void create() throws Exception {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        place = placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();

        String requestBody = objectMapper.writeValueAsString(commentCreate);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{placeId}", place.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("abc"))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    @WithMockUser
    void edit() throws Exception {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        place = placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();

        Comment comment = commentService.create(place.getId(), commentCreate);

        CommentEdit commentEdit = CommentEdit.builder()
                .name("abc")
                .content("bye")
                .build();

        String requestBody = objectMapper.writeValueAsString(commentEdit);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/comment/{commentId}", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bye"))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    @WithMockUser
    void delete() throws Exception {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        place = placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();

        Comment comment = commentService.create(place.getId(), commentCreate);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/{commentId}", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("abc"))
                .andDo(MockMvcResultHandlers.print());
    }
}