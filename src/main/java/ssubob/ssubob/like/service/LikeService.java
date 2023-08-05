package ssubob.ssubob.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssubob.ssubob.like.domain.Like;
import ssubob.ssubob.like.repository.LikeRepository;
import ssubob.ssubob.like.request.LikeRequest;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public Like get(LikeRequest likeRequest) {
        User user = userRepository.findByEmail(likeRequest.getUsername())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        return likeRepository.findByUserIdAndPlaceId(user.getId(), likeRequest.getPlaceId())
                .orElse(null);
    }

    public Like create(LikeRequest likeRequest) {
        Place place = placeRepository.findById(likeRequest.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));
        User user = userRepository.findByEmail(likeRequest.getUsername())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Like like = Like.builder()
                .place(place)
                .user(user)
                .build();
        return likeRepository.save(like);
    }

    public void delete(LikeRequest likeRequest) {
        User user = userRepository.findByEmail(likeRequest.getUsername())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Like like = likeRepository.findByUserIdAndPlaceId(user.getId(), likeRequest.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다."));
        likeRepository.delete(like);
    }
}
