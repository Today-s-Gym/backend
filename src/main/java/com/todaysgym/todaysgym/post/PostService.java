package com.todaysgym.todaysgym.post;

import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.PostErrorCode;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.PostRepository;
import com.todaysgym.todaysgym.post.dto.GetPostsRes;
import com.todaysgym.todaysgym.post.dto.PostPostReq;
import com.todaysgym.todaysgym.post.like.PostLikeService;
import com.todaysgym.todaysgym.post.photo.PostPhotoRepository;
import com.todaysgym.todaysgym.post.photo.PostPhotoService;
import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.record.dto.RecordGetRecentRes;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.record.photo.RecordPhotoRepository;
import com.todaysgym.todaysgym.record.photo.RecordPhotoService;
import com.todaysgym.todaysgym.utils.S3Service;
import com.todaysgym.todaysgym.utils.UtilService;
import com.todaysgym.todaysgym.utils.dto.getS3Res;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode.EMPTY_RECORD;
import static com.todaysgym.todaysgym.utils.UtilService.convertLocalDateTimeToTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostPhotoService postPhotoService;
    private final S3Service s3Service;
    private final UtilService utilService;
    private final PostLikeService postLikeService;

    /**
     * 기록과 관련된 게시글 조회 후 기록 null로 변경
     */
    public List<Post> deleteRecord(Long recordId){
        return postRepository.findPostByRecord(recordId);
    }

    @Transactional
    public String createPost(Member writer, PostPostReq postPostReq, List<MultipartFile> multipartFiles) throws BaseException {
        // 1. 기록 첨부 여부 조회 -> 기록 없으면 NULL로 저장
        Record record = null;
        if(postPostReq.getRecordId() != null) {
            record = utilService.findByRecordIdWithValidation(postPostReq.getRecordId());
        }
        // 2. 게시글 생성
        Post post = Post.createPost(postPostReq.getCategory(), writer, postPostReq.getTitle(), postPostReq.getContent(), record);
        postRepository.save(post);

        // 3. 이미지 첨부했다면 저장
        if(multipartFiles != null) {
            List<getS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            postPhotoService.saveAllPostPhotoByPost(imgUrls, post);
        }

        return "postId: " + post.getPostId() + "인 게시글을 생성했습니다.";
    }

    public List<GetPostsRes> getAllPosts(Member viewer, Category category, int page) throws BaseException {
        List<Post> posts = postRepository.findByCategoryId(category, PageRequest.of(page,10)).orElse(null);

        List<GetPostsRes> postsRes = new ArrayList<>();

        for(int i=0; i<posts.size(); i++) {
            Post post = posts.get(i);

            // 첨부된 기록 처리
            Record record = post.getRecord();
            RecordGetRecentRes recordRes = null;
            if(record != null) {
                recordRes = new RecordGetRecentRes(record.getRecordId(), record.getContent(), record.getCreatedAt(), record.getPhotoList());
            }

            GetPostsRes res = GetPostsRes.builder()
                    .postId(post.getPostId())
                    .postPhotoList(postPhotoService.findByPostId(post.getPostId()))
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(convertLocalDateTimeToTime(post.getCreatedAt()))
                    .writerId(post.getMember().getMemberId())
                    //.writerAvatarImgUrl(post.getMember().getAvatar())
                    .writerNickname(post.getMember().getNickName())
                    .likeCounts(postLikeService.getLikeCounts(post.getPostId()))
                    .liked(postLikeService.checkLike(viewer.getMemberId(), post.getPostId()))
                    .commentCounts(post.getCommentList().size())
                    .build();
        }

        return postsRes;
    }
}
