package ssubob.ssubob.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    public void clean() {
        commentRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    @Transactional
    public void create() {
        //given

        Authentication authentication = new UsernamePasswordAuthenticationToken("kim@naver.com", "1234");

        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .category("일식")
                .build());

        User user = User.builder()
            .email("kim@naver.com")
            .nickname("kim")
            .password("1234")
            .build();

        userRepository.save(user);

        CommentCreate commentCreate = CommentCreate.builder()
                .content("hello")
                .build();

        //when
        commentService.create(place.getId(), commentCreate, authentication);

        //then
        Comment comment = commentRepository.findAll().get(0);
        assertEquals(comment.getName(), "kim");
        assertEquals(comment.getPlace().getTitle(), "마루스시");
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    @Transactional
    public void delete() {
        //given
        Authentication authentication = new UsernamePasswordAuthenticationToken("kim@naver.com", "1234");

        Place place = Place.builder()
                .title("마루스시")
                .category("일식")
                .build();

        placeRepository.save(place);

        User user = User.builder()
            .email("kim@naver.com")
            .nickname("kim")
            .password("1234")
            .build();

        userRepository.save(user);

        Comment comment = Comment.builder()
            .name("kim")
            .user(user)
            .content("안녕하세요")
            .place(place)
            .build();

        commentRepository.save(comment);

        //when
        Comment deleteComment = commentService.delete(comment.getId(), authentication);

        //then
        assertEquals(deleteComment.getName(), "kim");
        assertEquals(deleteComment.getContent(), "안녕하세요");
        List<Comment> comments = commentRepository.findAll();
        assertEquals(comments.size(), 0);
    }
}