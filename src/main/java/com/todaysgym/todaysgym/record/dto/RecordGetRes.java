package com.todaysgym.todaysgym.record.dto;


import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.tag.dto.TagGetRes;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecordGetRes {
    public RecordGetRes(Record record, Member member) {
        this.content = record.getContent();
        this.createdTime = UtilService.convertLocalDateTimeToLocalDate(record.getCreatedAt());
        this.userName = member.getNickName();
        if(record.getPhotoList().isEmpty()) {
            this.recordPhotos = new ArrayList<>();
        }
        else {
            this.recordPhotos = record.getPhotoList().stream()
                    .map(recordPhoto -> new RecordPhotoGetRes(recordPhoto))
                    .collect(Collectors.toList());
        }
        this.tags = record.getTagList().stream()
                .map(tag -> new TagGetRes(tag))
                .collect(Collectors.toList());
    }
    private String content;
    private String createdTime;
    private String userName;
    private List<RecordPhotoGetRes> recordPhotos;
    private List<TagGetRes> tags;

}
