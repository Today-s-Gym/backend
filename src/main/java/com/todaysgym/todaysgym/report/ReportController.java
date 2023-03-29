package com.todaysgym.todaysgym.report;

import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.post.comment.Comment;
import com.todaysgym.todaysgym.report.dto.ReportReq;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final UtilService utilService;
    private final ReportService reportService;
    @PostMapping("/report/member")
    public BaseResponse<Long> reportMember(@RequestBody ReportReq reportReq) {
        Member reporter = utilService.findByMemberIdWithValidation(1L);
        Member reportedMember = utilService.findByMemberIdWithValidation(reportReq.getReportedId());
        return new BaseResponse<>(reportService.saveReportMember(reporter, reportedMember));
    }

    @PostMapping("/report/post")
    public BaseResponse<Long> reportPost(@RequestBody ReportReq reportReq) {
        Member reporter = utilService.findByMemberIdWithValidation(1L);
        Post reportedPost = utilService.findByPostIdWithValidation(reportReq.getReportedId());
        return new BaseResponse<>(reportService.saveReportPost(reporter, reportedPost));
    }

    @PostMapping("/report/comment")
    public BaseResponse<Long> reportComment(@RequestBody ReportReq reportReq) {
        Member reporter = utilService.findByMemberIdWithValidation(1L);
        Comment reportedComment = utilService.findByCommentIdWithValidation(
            reportReq.getReportedId());
        return new BaseResponse<>(reportService.saveReportComment(reporter, reportedComment));
    }
}