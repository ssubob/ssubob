package ssubob.ssubob.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.place.repository.PlaceRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PlaceRepository placeRepository;

    public void create(Long id, CommentCreate commentCreate) {
        Comment comment = Comment.builder()
                .name(commentCreate.getName())
                .content(commentCreate.getContent())
                .place(placeRepository.findById(id).orElseThrow())
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void edit(Long commentId, CommentEdit commentEdit) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.edit(commentEdit);
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }
}
