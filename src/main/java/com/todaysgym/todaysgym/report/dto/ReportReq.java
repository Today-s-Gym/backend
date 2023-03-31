package com.todaysgym.todaysgym.report.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportReq {

    @ApiModelProperty(value = "신고 당하는 것의 id", example = "1", required = true)
    private Long reportedId;
}