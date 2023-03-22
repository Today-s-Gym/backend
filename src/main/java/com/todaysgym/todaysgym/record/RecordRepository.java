package com.todaysgym.todaysgym.record;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> getByRecordId(Long recordId);

    @Query("select count(r) from Record r where FORMATDATETIME(r.createdAt, 'yyyy-MM-dd') = :date and r.member.memberId = :memberId")
    Integer findByRecordDate(@Param("date") String date, @Param("memberId") Long memberId);

}
