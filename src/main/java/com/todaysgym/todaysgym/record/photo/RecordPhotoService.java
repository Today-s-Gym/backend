package com.todaysgym.todaysgym.record.photo;

import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.utils.S3Service;
import com.todaysgym.todaysgym.utils.dto.getS3Res;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordPhotoService {

    private final RecordPhotoRepository recordPhotoRepository;
    private final S3Service s3Service;

    @Transactional
    public void saveRecordPhoto(List<RecordPhoto> recordPhotos){
        recordPhotoRepository.saveAll(recordPhotos);
    }

    /**
     *여러개의 recordPhoto 저장
     */
    @Transactional
    public void saveAllRecordPhotoByRecord(List<getS3Res> getS3ResList, Record record) {
        List<RecordPhoto> recordPhotos = new ArrayList<>();
        for(getS3Res getS3Res : getS3ResList){
            RecordPhoto recordPhoto = RecordPhoto.builder().imgUrl(getS3Res.getImgUrl()).fileName(getS3Res.getFileName()).build();
            recordPhotos.add(recordPhoto);
            record.createPhotoList(recordPhoto);
        }
        saveRecordPhoto(recordPhotos);
    }
}
