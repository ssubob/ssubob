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

@SpringBootTest
class PlaceServiceTest {

	@Autowired
	protected PlaceRepository placeRepository;

	@BeforeEach
	void clean() {
		placeRepository.deleteAll();
	}

	@Test
	@DisplayName("카테고리가 일식인 음식점 조회")
	void test1() {
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
		//when
		List<Place> placeList = placeRepository.findByCategory("일식");
		//Place findPlace = placeList.get(0);

		//then
		assertEquals(placeList.size(), 2);
	}

}
