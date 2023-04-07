package com.todaysgym.todaysgym.post.photo;

import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {

    @Query("SELECT pp FROM PostPhoto pp WHERE pp.post.postId = :postId")
    List<PostPhoto> findAllByPost(@Param("postId") Long postId);

    @Query("SELECT pp.id FROM PostPhoto pp WHERE pp.post.postId = :postId")
    List<Long> findAllId(@Param("postId") Long postId);

    @Modifying
    @Query("DELETE FROM PostPhoto pp WHERE pp.id IN :ids")
    Integer deleteAllByPost(@Param("ids") List<Long> ids);
}
