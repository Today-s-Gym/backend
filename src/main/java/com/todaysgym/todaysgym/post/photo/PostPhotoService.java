package com.todaysgym.todaysgym.post.photo;

import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import com.todaysgym.todaysgym.utils.S3Service;
import com.todaysgym.todaysgym.utils.dto.getS3Res;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostPhotoService {

    private final PostPhotoRepository postPhotoRepository;
    private final S3Service s3Service;

    @Transactional
    public void savePostPhoto(List<PostPhoto> postPhotos){
        postPhotoRepository.saveAll(postPhotos);
    }

    public List<PostPhoto> findByPostId(Long postId){ return postPhotoRepository.findAllByPost(postId); }

    /**
     *  여러개의 postPhoto 저장
     */
    @Transactional
    public void saveAllPostPhotoByPost(List<getS3Res> getS3ResList, Post post) {
        List<PostPhoto> postPhotos = new ArrayList<>();
        for(getS3Res getS3Res : getS3ResList){
            PostPhoto postPhoto = PostPhoto.builder()
                    .imgUrl(getS3Res.getImgUrl())
                    .fileName(getS3Res.getFileName())
                    .build();
            postPhotos.add(postPhoto);
            post.createPhotoList(postPhoto);
        }
        savePostPhoto(postPhotos);
    }

    /**
     * 게시글과 연관된 모든 postPhoto 삭제
     */
    @Transactional
    public void deleteAllPostPhotos(List<PostPhoto> postPhotos){
        for (PostPhoto postPhoto : postPhotos) {
            s3Service.deleteFile(postPhoto.getFileName());
        }
    }

    @Transactional
    public void deleteAllPostPhotoByPost(List<Long> ids){
        postPhotoRepository.deleteAllByPost(ids);
    }

    /**
     * post와 연관된 모든 id 조회
     */
    public List<Long> findAllId(Long postId){
        return postPhotoRepository.findAllId(postId);
    }
}
