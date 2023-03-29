package com.todaysgym.todaysgym.record.dto;

import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecordGetRecentRes {
    private Long recordId;
    private String content;
    private String createdTime;
    private String imgUrl;
    public RecordGetRecentRes(Long recordId, String content, LocalDateTime createdAt, List<RecordPhoto> recordPhotos) {
        this.recordId = recordId;
        this.content = content;
        this.createdTime = UtilService.convertLocalDateTimeToLocalDate(createdAt);
        if (recordPhotos.isEmpty()) {
            this.imgUrl =  "";
        } else {
            this.imgUrl = recordPhotos.get(0).getImgUrl();
        }
    }
}
