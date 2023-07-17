import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssubob.ssubob.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
	List<Place> findByCategory(String category);
}

