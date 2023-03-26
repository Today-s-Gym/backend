package com.todaysgym.todaysgym.record.dto;

import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import lombok.Data;

@Data
public class RecordPhotoGetRes {
    private String img_url;
    public RecordPhotoGetRes(RecordPhoto recordPhoto) {this.img_url = recordPhoto.getImgUrl();}
}
