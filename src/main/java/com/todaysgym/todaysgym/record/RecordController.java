package com.todaysgym.todaysgym.record;

import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
                                              @RequestPart(value = "recordGetReq") RecordGetReq recordGetReq){
            return new BaseResponse<>(recordService.saveRecord(multipartFiles, recordGetReq));
    }
}
