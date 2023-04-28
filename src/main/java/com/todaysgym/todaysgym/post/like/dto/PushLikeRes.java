package com.todaysgym.todaysgym.post.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PushLikeRes {

    private Long memberId;        // 좋아요를 누른 memberId
    private Long postId;          // 대상 postId
    private boolean status;       // 좋아요를 누른 상태면 true, 아니면 false
    private String result;        // 결과값
}
