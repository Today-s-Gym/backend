package com.todaysgym.todaysgym.post.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query(value = "SELECT l FROM PostLike l WHERE l.memberId = :memberId AND l.postId = :postId")
    Optional<PostLike> getLikeByMemberIdAndCardId(Long memberId, Long postId);

    @Query(value = "SELECT COUNT(l) FROM PostLike l WHERE l.postId = :postId AND l.status = true")
    Optional<Integer> getLikeCountsByPostId(Long postId);
}
