package ssubob.ssubob.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssubob.ssubob.like.domain.Like;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPlaceId(Long userId, Long placeId);
}
