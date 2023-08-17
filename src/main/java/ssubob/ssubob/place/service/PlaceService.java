package ssubob.ssubob.place.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.request.PlaceCreate;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<Place> getPlaceListByCategory(String category) {
        return placeRepository.findByCategory(category);
    }


    @Transactional
    public Place create(PlaceCreate placeCreate) {
        String category = placeCreate.getCategory_name();
        if (category.contains(">"))
            category = placeCreate.getCategory_name().split(">")[1].trim();
        Place place = Place.builder()
                .title(placeCreate.getPlace_name())
                .url(placeCreate.getPlace_url())
                .category(category)
                .locationX(Double.parseDouble(placeCreate.getX()))
                .locationY(Double.parseDouble(placeCreate.getY()))
                .phone(placeCreate.getPhone())
                .address_name(placeCreate.getAddress_name())
                .distance(Long.parseLong(placeCreate.getDistance()))
                .apiId(Long.parseLong(placeCreate.getId()))
                .image(placeCreate.getImage())
                .build();
        Place createdPlace = placeRepository.findByApiId(place.getApiId()).orElse(null);
        if (createdPlace == null)
            createdPlace = placeRepository.save(place);
        return createdPlace;
    }

    public boolean isLoaded(Long apiId) {
        if (placeRepository.findByApiId(apiId).orElse(null) != null)
            return true;
        return false;
    }

    public Place getPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));
    }

    public List<Place> getPlaceList() {
        return placeRepository.findAll();
    }
}
