package com.todaysgym.todaysgym.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private final String code;
    private final String message;
    private T result;

    public BaseResponse(T result) {
        this.code = "SUCCESS";
        this.message = "요청에 성공했습니다.";
        this.result = result;
    }
}