package com.todaysgym.todaysgym.post.like;

import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.login.jwt.JwtService;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.like.dto.PushLikeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final JwtService jwtService;

    /**
     * 좋아요 누르기
     * [POST] /post/like
     */
    @PostMapping("/post/like")
    public BaseResponse<PushLikeRes> pushLike(@RequestParam("postId") Long postId){
        Long memberId = jwtService.getMemberIdx();
            return new BaseResponse<>(postLikeService.pushLike(memberId, postId));

    }
}
