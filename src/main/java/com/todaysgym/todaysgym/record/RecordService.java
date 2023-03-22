package com.todaysgym.todaysgym.record;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.record.photo.RecordPhotoService;
import com.todaysgym.todaysgym.tag.TagService;
import com.todaysgym.todaysgym.utils.S3Service;
import com.todaysgym.todaysgym.utils.UtilService;
import com.todaysgym.todaysgym.utils.dto.getS3Res;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode.RECORD_DATE_EXISTS;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UtilService utilService;
    private final S3Service s3Service;
    private final RecordPhotoService recordPhotoService;
    private final TagService tagService;
    /**
     * Record, photo, tag 저장
     */
    @Transactional
    public String saveRecord(List<MultipartFile> multipartFiles, RecordGetReq recordGetReq) throws BaseException {
        validateDuplicateRecord();
        //1. 엔티티 조회
        Member member = utilService.findByMemberIdWithValidation(1L);
        //2. 기록 내용, 사용자 추가
        if(!member.isRecordCheck()) {
            member.addRecordCount();
        }
        member.updateRecordCheck();
        Record record = Record.createRecord(recordGetReq.getContent(), member);
        recordRepository.save(record);
        //3. 기록 사진 추가
        if(multipartFiles != null) {
            List<getS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            recordPhotoService.saveAllRecordPhotoByRecord(imgUrls, record);
        }
        //4. 태크 추가
        tagService.saveAllTagByRecord(recordGetReq, record);
        return "기록을 추가했습니다";
    }

    /**
     * 하루에 하나만 기록 추가
     */
    private void validateDuplicateRecord() throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Integer count = recordRepository.findByRecordDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter).toString() , member.getMemberId());
        if(count > 0){
            throw new BaseException(RECORD_DATE_EXISTS);
        }
    }
}
