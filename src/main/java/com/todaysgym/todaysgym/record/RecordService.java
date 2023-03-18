package com.todaysgym.todaysgym.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
}
