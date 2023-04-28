package com.todaysgym.todaysgym.post.dto;

import com.todaysgym.todaysgym.post.photo.PostPhoto;
import com.todaysgym.todaysgym.record.dto.RecordGetRecentRes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPostsRes {
    private Long postId;                    // 게시글 Id
    private String title;                   // 게시글 제목
    private String content;                 // 게시글 내용
    private List<PostPhoto> postPhotoList;     // 사진 리스트
    private String createdAt;                // 생성시간 -> 20초 전, 1분 전..
    private Integer likeCounts;             // 좋아요 개수
    private boolean liked;                  // viewer 가 좋아요를 눌렀는가
    private Integer commentCounts;          // 댓글 개수

    private Long writerId;                  // 작성자 Id
    private String writerAvatarImgUrl;      // 작성자 아바타 이미지
    private String writerNickname;          // 작성자 닉네임

    private RecordGetRecentRes record;      // 첨부된 기록
}
