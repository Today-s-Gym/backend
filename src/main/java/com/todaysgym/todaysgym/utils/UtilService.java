package com.todaysgym.todaysgym.utils;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.CommentErrorCode;
import com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode;
import com.todaysgym.todaysgym.config.exception.errorCode.PostErrorCode;
import com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.member.MemberRepository;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.post.PostRepository;
import com.todaysgym.todaysgym.post.comment.Comment;
import com.todaysgym.todaysgym.post.comment.CommentRepository;
import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.record.RecordRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilService {

    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RecordRepository recordRepository;


    public Member findByMemberIdWithValidation(Long memberId) throws BaseException {
        Member member = memberRepository.getByMemberId(memberId).orElse(null);
        if (member == null) {
            throw new BaseException(MemberErrorCode.EMPTY_MEMBER);
        }
        return member;
    }

    public Post findByPostIdWithValidation(Long postId) throws BaseException {
        Post post = postRepository.getByPostId(postId).orElse(null);
        if (post == null) {
            throw new BaseException(PostErrorCode.EMPTY_POST);
        }
        return post;
    }

    public Comment findByCommentIdWithValidation(Long commentId) throws BaseException {
        Comment comment = commentRepository.getByCommentId(commentId).orElse(null);
        if (comment == null) {
            throw new BaseException(CommentErrorCode.EMPTY_COMMENT);
        }
        return comment;
    }

    public Record findByRecordIdWithValidation(Long recordId) throws BaseException {
        Record record = recordRepository.getByRecordId(recordId).orElse(null);
        if (record == null) {
            throw new BaseException(RecordErrorCode.EMPTY_RECORD);
        }
        return record;
    }

    public static String convertLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String convertLocalDateTimeToTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        String msg = null;
        if (diffTime < SEC) {
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / MONTH;
        return diffTime + "년 전";
    }
}