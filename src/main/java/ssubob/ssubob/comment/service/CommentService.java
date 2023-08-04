package ssubob.ssubob.comment.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.comment.repository.CommentRepository;
import ssubob.ssubob.comment.request.CommentCreate;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public Comment create(Long id, CommentCreate commentCreate, Authentication authentication) {

        User findUser = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저입니다."));

        Comment comment = Comment.builder()
                .name(findUser.getNickname())
                .content(commentCreate.getContent())
                .place(placeRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다.")))
                .user(findUser)
                .build();
        commentRepository.save(comment);
        return comment;
    }

    public Comment delete(Long commentId, Authentication authentication) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow((() -> new IllegalArgumentException("댓글이 존재하지 않습니다.")));

        User findUser = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if(!comment.getName().equals(findUser.getNickname())){
            throw new IllegalArgumentException("댓글 주인이 아닙니다.");
        }

        commentRepository.delete(comment);
        return comment;
    }
}
