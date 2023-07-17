package ssubob.ssubob.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssubob.ssubob.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
