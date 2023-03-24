package com.todaysgym.todaysgym.record;

import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.record.dto.RecordGetRecentRes;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.record.dto.RecordGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    /**
     * 사진 여러개, 태그 여러개 저장
     */
    @PostMapping("/record")
    public BaseResponse<String> createRecord(@RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                              @RequestPart(value = "recordGetReq") @Valid RecordGetReq recordGetReq){
            return new BaseResponse<>(recordService.saveRecord(multipartFiles, recordGetReq));
    }

    /**
     * Record, 사진, 태그 조회 (달 기준)
     */
    @GetMapping("/record/month")
    public BaseResponse<List<RecordGetRes>> getRecordMonth(@Param("month") String month){
         return new BaseResponse<>(recordService.findRecordByMonth(month));
    }

    /**
     * Record, 사진, 태그 조회 (날짜 기준)
     */
    @GetMapping("/record/day")
    public BaseResponse<RecordGetRes> getRecordDay(@Param("date") String date){
        return new BaseResponse<>(recordService.findRecordByDay(date));
    }

    /**
     * 기록 수정하기
     */
    @PostMapping("/record/update")
    public BaseResponse<String> updateRecord(@Param("date") String date,
                                              @RequestPart(value = "recordGetReq") @Valid RecordGetReq recordGetReq,
                                              @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles){
        return new BaseResponse<>(recordService.updateRecord(date, recordGetReq, multipartFiles));
    }

    /**
     * 기록 삭제하기
     */
    @PostMapping("/record/delete")
    public BaseResponse<String> deleteRecord(@Param("date") String date){
        return new BaseResponse<>(recordService.deleteRecord(date));
    }

    /**
     * 최근 기록 조회하기
     */
    @GetMapping("/record/recent")
    public Page<RecordGetRecentRes> findRecentRecord(@Param("page") int page){
        return recordService.findRecordRecent(page);
    }

    /**
     * 기록 신고하기
     */
    @GetMapping("/record/report")
    public BaseResponse<String> reportRecord(@Param("date") String date){
        return new BaseResponse<>(recordService.reportRecord(date));
    }
}
