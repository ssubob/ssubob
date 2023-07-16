package ssubob.ssubob.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.service.PlaceService;

@RestController
@RequiredArgsConstructor
public class PlaceController {

	private final PlaceService placeService;
	@GetMapping("/place/{category}")
	public List<Place> getPlaceList(@PathVariable String category){
		return placeService.getPlaceList(category);

	}

}
