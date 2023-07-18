package ssubob.ssubob.place.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.request.PlaceCreate;

@Service
@RequiredArgsConstructor
public class PlaceService {
	private final PlaceRepository placeRepository;

	public List<Place> getPlaceList(String category) {
		return placeRepository.findByCategory(category);
	}


	public Place create(PlaceCreate placeCreate){
		String category = placeCreate.getCategory_name();
		if(category.contains(">"))
			category = placeCreate.getCategory_name().split(">")[1].trim();
		Place place = Place.builder()
				.title(placeCreate.getPlace_name())
				.url(placeCreate.getPlace_url())
				.category(category)
				.locationX(Double.valueOf(placeCreate.getX()))
				.locationY(Double.valueOf(placeCreate.getY()))
				.build();
		return placeRepository.save(place);
	}

	public Place getPlace(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));
	}
}
