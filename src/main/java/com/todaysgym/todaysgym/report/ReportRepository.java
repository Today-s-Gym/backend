package com.todaysgym.todaysgym.report;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.reporterId =:reporterId and r.reportedId =:reportedId and r.type = 'MEMBER'")
    Optional<Report> findMemberReport(@Param("reporterId") Long reporterId, @Param("reportedId") Long reportedId);

    @Query("select r from Report r where r.reporterId =:reporterId and r.reportedId =:reportedId and r.type = 'POST'")
    Optional<Report> findPostReport(@Param("reporterId") Long reporterId, @Param("reportedId") Long reportedId);

    @Query("select r from Report r where r.reporterId =:reporterId and r.reportedId =:reportedId and r.type = 'COMMENT'")
    Optional<Report> findCommentReport(@Param("reporterId") Long reporterId, @Param("reportedId") Long reportedId);
}
