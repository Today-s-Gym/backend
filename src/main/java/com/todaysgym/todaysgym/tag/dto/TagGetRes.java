package com.todaysgym.todaysgym.tag.dto;

import com.todaysgym.todaysgym.tag.Tag;
import lombok.Data;

@Data
public class TagGetRes {
    private String name;
    public TagGetRes(Tag tag) {
        this.name = tag.getName();
    }
}
