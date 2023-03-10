package com.todaysgym.todaysgym.report;

import com.todaysgym.todaysgym.utils.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private Long reporterId; // 신고하는 유저의 ID
    private Long reportedId; // 신고당하는 것의 ID
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType type; // 신고 당하는 것이 무엇인지 구별 (MEMBER, POST, COMMENT 중 1개)
}