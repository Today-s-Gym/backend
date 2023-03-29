package com.todaysgym.todaysgym.post;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.post.PostRepository;
import com.todaysgym.todaysgym.record.photo.RecordPhotoRepository;
import com.todaysgym.todaysgym.record.photo.RecordPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode.EMPTY_RECORD;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**
     * 기록과 관련된 게시글 조회 후 기록 null로 변경
     */
    public List<Post> deleteRecord(Long recordId){
        return postRepository.findPostByRecord(recordId);
    }
}
