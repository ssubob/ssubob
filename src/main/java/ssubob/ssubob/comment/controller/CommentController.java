package ssubob.ssubob.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.comment.service.CommentService;
import ssubob.ssubob.place.domain.Place;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{placeId}")
    public Comment create(@PathVariable Long placeId, @RequestBody @Valid CommentCreate commentCreate) {
        return commentService.create(placeId, commentCreate);
    }

    @PatchMapping("/comment/{commentId}")
    public Comment edit(@PathVariable Long commentId, @RequestBody @Valid CommentEdit commentEdit) {
        return commentService.edit(commentId, commentEdit);
    }

    @DeleteMapping("/comment/{commentId}")
    public Comment delete(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }
}
