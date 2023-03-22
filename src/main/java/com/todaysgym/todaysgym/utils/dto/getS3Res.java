package com.todaysgym.todaysgym.utils.dto;


import lombok.Data;

@Data
public class getS3Res {
    private String imgUrl;
    private String fileName;

    public getS3Res(String imgUrl, String fileName) {
        this.imgUrl = imgUrl;
        this.fileName = fileName;
    }
}
