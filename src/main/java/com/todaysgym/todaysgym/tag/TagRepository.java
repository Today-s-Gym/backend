package com.todaysgym.todaysgym.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t.id from Tag t where t.record.recordId = :recordId")
    List<Long> findAllId(@Param("recordId") Long recordId);

    //@Query("select distinct t.name from Tag t where t.member.memberId = :memberId")
    //Page<String> findByRecord(@Param("userId") Long memberId, Pageable pageable);

    @Modifying
    @Query("delete from Tag t where t.id in :ids")
    Integer deleteAllByRecord(@Param("ids") List<Long> ids);

}
