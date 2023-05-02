package com.todaysgym.todaysgym.record;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.login.jwt.JwtService;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.member.MemberService;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.post.PostService;
import com.todaysgym.todaysgym.record.dto.RecordGetRecentRes;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.record.dto.RecordGetRes;
import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import com.todaysgym.todaysgym.record.photo.RecordPhotoService;
import com.todaysgym.todaysgym.tag.TagService;
import com.todaysgym.todaysgym.utils.S3Service;
import com.todaysgym.todaysgym.utils.UtilService;
import com.todaysgym.todaysgym.utils.dto.getS3Res;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode.EMPTY_RECORD;
import static com.todaysgym.todaysgym.config.exception.errorCode.RecordErrorCode.RECORD_DATE_EXISTS;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UtilService utilService;
    private final S3Service s3Service;
    private final RecordPhotoService recordPhotoService;
    private final TagService tagService;
    private final PostService postService;
    private final MemberService memberService;
    private final JwtService jwtService;
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
        memberService.checkAndMyAvatarLevelUp(jwtService.getMemberIdx());
        return "기록을 추가했습니다";
    }

    /**
     * 하루에 하나만 기록 추가
     */
    private void validateDuplicateRecord() throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Integer count = recordRepository.findByRecordCount(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter).toString() , member.getMemberId());
        if(count > 0) throw new BaseException(RECORD_DATE_EXISTS);

    }

    /**
     * y-m-d에 따라 기록 조회
     */
    public RecordGetRes findRecordByDay(String date) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        Record record = recordRepository.findByRecordDate(date, member.getMemberId());
        if(record == null) throw new BaseException(EMPTY_RECORD);
        record = utilService.findByRecordIdWithValidation(record.getRecordId());
        RecordGetRes recordGetRes = new RecordGetRes(record, member);
        return recordGetRes;
    }

    /**
     * y-m에 따라 기록 조회
     */
    public List<RecordGetRes> findRecordByMonth(String month) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        List<Record> records = recordRepository.findByRecordMonth(month, member.getMemberId());
        if(records.isEmpty()) throw new BaseException(EMPTY_RECORD);
        List<RecordGetRes> recordGetRes = records.stream()
                .map(record-> new RecordGetRes(record, member))
                .collect(Collectors.toList());
        return recordGetRes;
    }

    /**
     * 기록 수정하기
     */
    @Transactional
    @Modifying
    public String updateRecord(String date, RecordGetReq recordGetReq, List<MultipartFile> multipartFiles) throws BaseException {
        //1. User, Record 조회 한 후 Record update
        Member member = utilService.findByMemberIdWithValidation(1L);
        Record record = recordRepository.findByRecordDate(date, member.getMemberId());
        if(record == null) throw new BaseException(EMPTY_RECORD);
        record = utilService.findByRecordIdWithValidation(record.getRecordId());
        record.updateRecord(recordGetReq.getContent());

        //2. RecordPhoto 삭제
        List<RecordPhoto> recordPhotos = recordPhotoService.findByRecordId(record.getRecordId());
        recordPhotoService.deleteAllRecordPhotos(recordPhotos);
        List<Long> ids = recordPhotoService.findAllId(record.getRecordId());
        recordPhotoService.deleteAllRecordPhotoByRecord(ids);
        //3. RecordPhoto 추가
        if(multipartFiles != null) {
            List<getS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            recordPhotoService.saveAllRecordPhotoByRecord(imgUrls, record);
        }

        //Tag 수정
        List<Long> tIds = tagService.findAllId(record.getRecordId());
        System.out.println(tIds.size());
        tagService.deleteAllTagByRecord(tIds);
        tagService.saveAllTagByRecord(recordGetReq,record);
        return "기록을 수정했습니다.";
    }

    /**
     * 기록 삭제하기 연관된 사진, 태그도 모두 삭제
     * (태그를 삭제하면 최근 사용한 태그를 조회 불가)
     */
    @Transactional
    @Modifying
    public String deleteRecord(String date) throws BaseException {
        //1. 로그인 되어 있는 멤버와 관련된 기록 가져옴
        Member member = utilService.findByMemberIdWithValidation(1L);
        Record record = recordRepository.findByRecordDate(date, member.getMemberId());
        if(record == null) throw new BaseException(EMPTY_RECORD);

        //2. recordPhoto 삭제
        List<RecordPhoto> recordPhotos = recordPhotoService.findByRecordId(record.getRecordId());
        recordPhotoService.deleteAllRecordPhotos(recordPhotos);
        List<Long> ids = recordPhotoService.findAllId(record.getRecordId());
        recordPhotoService.deleteAllRecordPhotoByRecord(ids);

        //3. 태그 삭제
        List<Long> tIds = tagService.findAllId(record.getRecordId());
        tagService.deleteAllTagByRecord(tIds);

        //4. 기록과 관련된 게시글에 기록 삭제
        List<Post> posts = postService.deleteRecord(record.getRecordId());

        if(!posts.isEmpty()){
           for(Post post : posts){
               post.deleteRecord();
           }
        }
        System.out.println(posts.size());
        //Record 삭제
        recordRepository.deleteAllByRecordId(record.getRecordId());
        return "기록을 삭제했습니다.";
    }


    /**
     * 최근 기록 조회
     */
    public Page<RecordGetRecentRes> findRecordRecent(int page) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Record> records = recordRepository.findAllByUserId(member.getMemberId(), pageRequest);
        if (records.getTotalElements() == 0)  throw new BaseException(EMPTY_RECORD);
        Page<RecordGetRecentRes> results = records.map(r -> new RecordGetRecentRes(r.getRecordId(), r.getContent(), r.getCreatedAt(), r.getPhotoList()));
        return results;
    }

    /**
     * 기록 신고하기
     */
    @Transactional
    public String reportRecord(String date) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(1L);
        Record record = recordRepository.findByRecordDate(date, member.getMemberId());
        record.addReport();
        return "신고가 접수되었습니다";
    }

}
