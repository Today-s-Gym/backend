package com.todaysgym.todaysgym.record.dto;

import com.todaysgym.todaysgym.tag.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordGetReq {
    private String content;
    private List<Tag> tags = new ArrayList<>();
}
