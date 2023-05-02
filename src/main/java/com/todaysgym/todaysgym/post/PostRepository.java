package com.todaysgym.todaysgym.post;

import java.util.List;
import java.util.Optional;

import com.todaysgym.todaysgym.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getByPostId(Long postId);

    @Query("select p from Post p where p.record.recordId = :recordId")
    List<Post> findPostByRecord(@Param("recordId") Long recordId);

    @Query(value = "SELECT p FROM Post p WHERE p.category= :category ORDER BY p.postId DESC")
    Optional<List<Post>> findByCategoryId(Category category, Pageable limit);

}
