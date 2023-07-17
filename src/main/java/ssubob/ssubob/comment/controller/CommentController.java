package ssubob.ssubob.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public void create(@PathVariable Long postId, @RequestBody @Valid CommentCreate commentCreate) {
        commentService.create(postId, commentCreate);
    }

    @PatchMapping("comment/{commentId}")
    public void edit(@PathVariable Long commentId, @RequestBody @Valid CommentEdit commentEdit) {
        commentService.edit(commentId, commentEdit);
    }

    @DeleteMapping("comment/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
