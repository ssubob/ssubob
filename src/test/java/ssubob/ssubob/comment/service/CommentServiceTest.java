package ssubob.ssubob.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    public void clean() {
        commentRepository.deleteAll();
        placeRepository.deleteAll();
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    public void create() {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();
        Place findPlace = placeRepository.findById(place.getId()).orElseThrow(() -> new RuntimeException());

        //when
        commentService.create(place.getId(), commentCreate);

        //then
        Comment comment = commentRepository.findAll().get(0);
        assertEquals(comment.getName(), "abc");
        assertEquals(findPlace.getTitle(), "마루스시");
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    public void edit() {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();
        Place findPlace = placeRepository.findById(place.getId()).orElseThrow(() -> new RuntimeException());

        Comment comment = commentService.create(place.getId(), commentCreate);

        //when
        CommentEdit commentEdit = CommentEdit.builder()
                .name("abc")
                .content("bye")
                .build();

        Comment editedComment = commentService.edit(comment.getId(), commentEdit);
        //then
        assertNotEquals(editedComment.getContent(), "hello");
        assertEquals(editedComment.getContent(), "bye");
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    public void delete() {
        //given
        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        placeRepository.save(place);

        CommentCreate commentCreate = CommentCreate.builder()
                .name("abc")
                .content("hello")
                .build();
        Place findPlace = placeRepository.findById(place.getId()).orElseThrow(() -> new RuntimeException());

        Comment comment = commentService.create(place.getId(), commentCreate);

        //when
        Comment deletedComment = commentService.delete(comment.getId());

        //then
        List<Comment> comments = commentRepository.findAll();
        assertEquals(comments.size(), 0);
        assertEquals(deletedComment.getName(), "abc");
    }
}