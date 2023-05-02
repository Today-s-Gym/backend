package com.todaysgym.todaysgym.post.like;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.post.like.dto.PushLikeRes;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final UtilService utilService;

    @Transactional
    public void save(PostLike like) {
        postLikeRepository.save(like);
    }

    public void createLike(Long memberId, Long postId) {
        PostLike like = PostLike.builder()
                .memberId(memberId)
                .postId(postId)
                .status(true)
                .build();
        save(like);
    }

    public boolean checkLike(Long memberId, Long postId) {
        PostLike like = postLikeRepository.getLikeByMemberIdAndCardId(memberId, postId).orElse(null);
        //비어있으면 안 누른 거!!
        if (like == null) {
            return false;
        } else {
            if(!like.isStatus()) return false;
        }
        return true;
    }

    public Integer getLikeCounts(Long postId) {
        return postLikeRepository.getLikeCountsByPostId(postId).orElse(0);
    }


    @Transactional
    public PushLikeRes pushLike(Long memberId, Long postId) throws BaseException {
        utilService.findByPostIdWithValidation(postId);
        PostLike like = postLikeRepository.getLikeByMemberIdAndCardId(memberId, postId).orElse(null);

        String result = "";
        boolean status;
        // 새로 누르는 좋아요
        if (like == null) {
            createLike(memberId, postId);
            result = "좋아요를 누름";
            status = true;
        }
        // 좋아요 상태 변경
        else {
            result = "좋아요 상태 변경 성공";
            //좋아요 누른 상태였으면 취소로 변경, 아니었으면 누른 걸로 변경
            if(like.isStatus() == true) {
                status = false;
            } else {
                status = true;
            }
            like.changeStatus();
        }
        return new PushLikeRes(memberId, postId, status, result);
    }
}
