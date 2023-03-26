package com.todaysgym.todaysgym.record.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordPhotoRepository extends JpaRepository<RecordPhoto, Long> {

    @Query("select r from RecordPhoto r where r.record.recordId = :recordId")
    List<RecordPhoto> findAllByRecord(@Param("recordId") Long recordId);

    @Query("select rp.id from RecordPhoto rp where rp.record.recordId = :recordId")
    List<Long> findAllId(@Param("recordId") Long recordId);

    @Modifying
    @Query("delete from RecordPhoto rp where rp.id in :ids")
    Integer deleteAllByRecord(@Param("ids") List<Long> ids);
}
