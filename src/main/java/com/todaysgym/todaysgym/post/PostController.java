package com.todaysgym.todaysgym.post;

import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.login.jwt.JwtService;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.dto.GetPostRes;
import com.todaysgym.todaysgym.post.dto.GetPostsRes;
import com.todaysgym.todaysgym.post.dto.PostPostReq;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.report.dto.ReportReq;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final UtilService utilService;
    private final PostService postService;
    private final JwtService jwtService;

    /** 게시글 생성하기
     *  [POST] /post
     */
    @PostMapping("/post")
    public BaseResponse<String> createPost(@RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                           @RequestPart(value = "postPostReq") @Valid PostPostReq postPostReq){
            Member writer = utilService.findByMemberIdWithValidation(1L);
            return new BaseResponse<>(postService.createPost(writer, postPostReq, multipartFiles));
    }

    /** 전체 게시글 조회하기
     *  [GET] /posts/{page}
     *  주의사항: Param 에서 Category 반드시 대문자로 입력해야함!
     */
    @GetMapping("/posts/{page}")
    public BaseResponse<List<GetPostsRes>> getAllPostsByCategory(@PathVariable("page") int page, @RequestParam("category") Category category) {
            Member viewer = utilService.findByMemberIdWithValidation(jwtService.getMemberIdx());
            return new BaseResponse<>(postService.getAllPosts(viewer, category, page));
    }

    /** 상세 게시글 조회하기
     *  [GET] /post/{postId}
     */
    @GetMapping("/post/{postId}")
    public BaseResponse<GetPostRes> getPostByPostId(@PathVariable("postId") Long postId) {
        Member viewer = utilService.findByMemberIdWithValidation(jwtService.getMemberIdx());
        return new BaseResponse<>(postService.getPost(viewer, postId));
    }

}
