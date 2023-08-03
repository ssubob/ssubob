package ssubob.ssubob.place.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.request.PlaceCreate;

@SpringBootTest
class PlaceServiceTest {

    @Autowired
    protected PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @BeforeEach
    void clean() {
        placeRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리가 일식인 음식점 조회")
    void test1() {
        //given
        PlaceCreate place = PlaceCreate.builder()
                .place_name("마루스시")
                .place_url("www.test.com")
                .category_name("일식")
                .x("321.3")
                .y("123.4")
                .phone("010-1234-1234")
                .address_name("가나다")
                .distance("732")
                .id("5")
                .image("www.image.com")
                .build();

        PlaceCreate place2 = PlaceCreate.builder()
				.place_name("은화수식당")
				.place_url("www.test.com")
				.category_name("일식")
				.x("321.3")
				.y("123.4")
				.phone("010-1234-1234")
				.address_name("가나다")
				.distance("732")
				.id("7")
				.image("www.image.com")
				.build();

        //when
        placeService.create(place);
        placeService.create(place2);

        //then
        List<Place> placeList = placeRepository.findByCategory("일식");
        assertEquals(placeList.size(), 2);
    }

}
