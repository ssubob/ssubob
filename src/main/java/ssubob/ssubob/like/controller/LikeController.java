package ssubob.ssubob.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ssubob.ssubob.like.domain.Like;
import ssubob.ssubob.like.request.LikeRequest;
import ssubob.ssubob.like.service.LikeService;
import ssubob.ssubob.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;
    private final UserRepository userRepository;

    @GetMapping("/get/{placeId}")
    public ResponseEntity<Like> get(Authentication authentication, @PathVariable Long placeId) {
        LikeRequest likeRequest= LikeRequest.builder()
                .username(authentication.getName())
                .placeId(placeId)
                .build();
        Like like = likeService.get(likeRequest);
        if (like == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(like);
    }

    @PostMapping("/create/{placeId}")
    public ResponseEntity<Like> create(Authentication authentication, @PathVariable Long placeId) {
        LikeRequest likeRequest= LikeRequest.builder()
                .username(authentication.getName())
                .placeId(placeId)
                .build();
        Like like = likeService.create(likeRequest);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping("/delete/{placeId}")
    public ResponseEntity delete(Authentication authentication, @PathVariable Long placeId) {
        LikeRequest likeRequest= LikeRequest.builder()
                .username(authentication.getName())
                .placeId(placeId)
                .build();
        likeService.delete(likeRequest);
        return ResponseEntity.ok().build();
    }
}
