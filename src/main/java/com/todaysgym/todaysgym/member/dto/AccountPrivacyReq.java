package com.todaysgym.todaysgym.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountPrivacyReq {

    @ApiModelProperty(value = "공개 계정 여부", example = "true", required = true)
    private String locked;
}