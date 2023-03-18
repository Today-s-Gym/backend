package com.todaysgym.todaysgym.record;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecordController {

    //일단위 기록 조회 필요 없음

    private final RecordService recordService;

}
