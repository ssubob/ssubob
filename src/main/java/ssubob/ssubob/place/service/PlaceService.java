package ssubob.ssubob.place.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {
	private final PlaceRepository placeRepository;
	public List<Place> getPlaceList(String category) {
		return placeRepository.findByCategory(category);
	}

	public Place getPlace(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));
	}
}
