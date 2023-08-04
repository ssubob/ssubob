package ssubob.ssubob.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.comment.service.CommentService;
import ssubob.ssubob.place.domain.Place;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{placeId}")
    public Comment create(@PathVariable Long placeId, @RequestBody @Valid CommentCreate commentCreate, Authentication authentication) {
        return commentService.create(placeId, commentCreate, authentication);
    }

    @DeleteMapping("/comment/{commentId}")
    public Comment delete(@PathVariable Long commentId, Authentication authentication) {
        return commentService.delete(commentId, authentication);
    }
}
