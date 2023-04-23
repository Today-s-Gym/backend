package com.todaysgym.todaysgym.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberRecordCount {
    private int thisMonthRecord;
    private int remainUpgradeCount;
    private int cumulativeCount;

    public MemberRecordCount(int thisMonthRecord, int remainUpgradeCount, int cumulativeCount) {
        this.thisMonthRecord = thisMonthRecord;
        this.remainUpgradeCount = remainUpgradeCount;
        this.cumulativeCount = cumulativeCount;
    }
}
