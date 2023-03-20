package com.todaysgym.todaysgym.post.comment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> getByCommentId(Long commentId);

}
