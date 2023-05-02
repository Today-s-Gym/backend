package com.todaysgym.todaysgym.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPostRes {
    private GetPostsRes getPostsRes;    // 게시글 형식
    private String type;                // 내 것인지 아닌지 확인
}
