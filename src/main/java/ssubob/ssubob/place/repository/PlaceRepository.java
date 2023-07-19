package ssubob.ssubob.place.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssubob.ssubob.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
	List<Place> findByCategory(String category);
	Optional<Place> findByApiId(Long apiId);
}

