package com.todaysgym.todaysgym.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountPrivacyRes {
    @ApiModelProperty(example = "공개 계정 여부")
    private boolean locked;
}
