package ssubob.ssubob.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.service.PlaceService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PlaceController {

	private final PlaceService placeService;
	@GetMapping("/place/category/{category}")
	public ResponseEntity<List<Place>> getPlaceListByCategory(@PathVariable String category){
		List<Place> places = placeService.getPlaceListByCategory(category);
		return ResponseEntity.ok(places);
	}

	@GetMapping("/place/{placeId}")
	public Place getPlace(@PathVariable Long placeId){
		return placeService.getPlace(placeId);
	}

	@GetMapping("/place")
	public List<Place> getPlaceList(){
		return placeService.getPlaceList();
	}



}
