package ssubob.ssubob.like.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import ssubob.ssubob.like.domain.Like;
import ssubob.ssubob.like.repository.LikeRepository;
import ssubob.ssubob.like.request.LikeRequest;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.service.PlaceService;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeServiceTest {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;

    @BeforeEach
    void clean() {
        likeRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("좋아요한 식당과의 좋아요 여부를 확인한다.")
    @Transactional//?
    void get() {
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

        //when
        LikeRequest likeRequest = LikeRequest.builder()
                        .username(user.getUsername())
                                .placeId(place.getId())
                                        .build();

        Like like = likeService.get(likeRequest);

        //then
        assertEquals(like.getPlace().getTitle(),place.getTitle());
        assertEquals(like.getUser().getUsername(),user.getEmail());
    }

    @Test
    @DisplayName("좋아요하지 않은 식당과의 좋아요 여부를 확인한다.")
    void getWhenFailed(){
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        //when
        LikeRequest likeRequest = LikeRequest.builder()
                .username(user.getUsername())
                .placeId(place.getId())
                .build();

        Like like = likeService.get(likeRequest);

        //then
        assertNull(like);
    }

    @Test
    @DisplayName("특정 식당에 좋아요를 설정한다.")
    @Transactional
    void create() {
        //given
        Place place = placeRepository.save(Place.builder()
                .title("마루스시")
                .build());

        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .nickname("tom")
                .password("abcdefg")
                .build());

        //when
        LikeRequest likeRequest = LikeRequest.builder()
                .username(user.getUsername())
                .placeId(place.getId())
                .build();

        likeService.create(likeRequest);

        //then
        Like like = likeRepository.findByUserIdAndPlaceId(user.getId(), place.getId())
                .orElse(null);
        assertNotNull(like);
        assertEquals(like.getPlace().getTitle(),place.getTitle());
        assertEquals(like.getUser().getUsername(),user.getEmail());
    }

    @Test
    @DisplayName("특정 식당에 좋아요를 해제한다.")
    void delete() {
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

        //when
        LikeRequest likeRequest = LikeRequest.builder()
                .username(user.getUsername())
                .placeId(place.getId())
                .build();

        likeService.delete(likeRequest);

        //then
        Like like = likeRepository.findByUserIdAndPlaceId(user.getId(), place.getId())
                .orElse(null);
        assertNull(like);
    }
}