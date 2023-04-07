package com.todaysgym.todaysgym.post;

import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.dto.PostPostReq;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.report.dto.ReportReq;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final UtilService utilService;
    private final PostService postService;

    /** 게시글 생성하기
     *  [POST] /post
     */
    @PostMapping("/post")
    public BaseResponse<String> createPost(@RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                           @RequestPart(value = "postPostReq") @Valid PostPostReq postPostReq){
            Member writer = utilService.findByMemberIdWithValidation(1L);
            return new BaseResponse<>(postService.createPost(writer, postPostReq, multipartFiles));
    }

}
