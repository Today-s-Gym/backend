package com.todaysgym.todaysgym.report;

import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_COMMENT_DUPLICATE;
import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_COMMENT_SELF;
import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_MEMBER_DUPLICATE;
import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_MEMBER_SELF;
import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_POST_DUPLICATE;
import static com.todaysgym.todaysgym.config.exception.errorCode.ReportErrorCode.REPORT_POST_SELF;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.post.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public Long saveReportMember(Member member, Member reportedMember) {
        validateReportedMember(member, reportedMember);
        Report report = Report.createReportMember(member, reportedMember);
        reportedMember.addReportCount();
        reportRepository.save(report);
        return report.getReportId();
    }

    private void validateReportedMember(Member member, Member reportedMember) {
        if (member.equals(reportedMember)) {
            throw new BaseException(REPORT_MEMBER_SELF);
        }
        if (reportRepository.findMemberReport(member.getMemberId(), reportedMember.getMemberId())
            .isPresent()) {
            throw new BaseException(REPORT_MEMBER_DUPLICATE);
        }
    }

    @Transactional
    public Long saveReportPost(Member reporter, Post reportedPost) throws BaseException {
        validateReportedPost(reporter, reportedPost);
        Report report = Report.createReportPost(reporter, reportedPost);
        reportedPost.addReportCount();
        reportRepository.save(report);
        return report.getReportId();
    }

    private void validateReportedPost(Member reporter, Post reportedPost) throws BaseException {
        if (reporter.equals(reportedPost.getMember())) {
            throw new BaseException(REPORT_POST_SELF);
        }
        if (reportRepository.findPostReport(reporter.getMemberId(), reportedPost.getPostId())
            .isPresent()) {
            throw new BaseException(REPORT_POST_DUPLICATE);
        }
    }

    @Transactional
    public Long saveReportComment(Member reporter, Comment reportedComment) throws BaseException {
        validateReportedComment(reporter, reportedComment);
        Report report = Report.createReportComment(reporter, reportedComment);
        reportedComment.addReportCount();
        reportRepository.save(report);
        return report.getReportId();
    }

    private void validateReportedComment(Member reporter, Comment reportedComment)
        throws BaseException {
        if (reporter.equals(reportedComment.getMember())) {
            throw new BaseException(REPORT_COMMENT_SELF);
        }
        if (reportRepository.findCommentReport(reporter.getMemberId(),
            reportedComment.getCommentId()).isPresent()) {
            throw new BaseException(REPORT_COMMENT_DUPLICATE);
        }
    }
}
