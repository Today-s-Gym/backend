package com.todaysgym.todaysgym.post.dto;

import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.tag.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostPostReq {
    @NotNull
    private Category category;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private Long recordId; // null 일 수도 있음

}
