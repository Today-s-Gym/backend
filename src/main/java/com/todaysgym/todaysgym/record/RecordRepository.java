package com.todaysgym.todaysgym.record;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> getByRecordId(Long recordId);

    @Query("select count(r) from Record r where date_format(r.createdAt, '%Y-%m-%d')  = :date and r.member.memberId = :memberId")
    Integer findByRecordCount(@Param("date") String date, @Param("memberId") Long memberId);

    @Query("select r from Record r where date_format(r.createdAt, '%Y-%m-%d')  = :date and r.member.memberId = :memberId")
    Record findByRecordDate(@Param("date") String date, @Param("memberId") Long memberId);

    @Query("select r from Record r where date_format(r.createdAt, '%Y-%m') = :month and r.member.memberId = :memberId")
    List<Record> findByRecordMonth(@Param("month") String month, @Param("memberId") Long memberId);

    @Query("select count(r) from Record r where date_format(r.createdAt, '%Y-%m') = :month and r.member.memberId = :memberId")
    Integer countByMemberIdMonth(@Param("memberId") Long memberId, @Param("month") String month);

    @Modifying
    @Query("delete from Record r where r.recordId = :recordId")
    Integer deleteAllByRecordId(@Param("recordId") Long recordId);

    @Query("select r from Record r where r.member.memberId = :memberId")
    Page<Record> findAllByUserId(@Param("memberId") Long memberId, PageRequest pageRequest);
}
