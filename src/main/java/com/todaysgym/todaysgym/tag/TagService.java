package com.todaysgym.todaysgym.tag;

import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.record.dto.RecordGetReq;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final UtilService utilService;
    /**
     * 태크 여러개 저장
     */
    @Transactional
    public void saveTag(List<Tag> tags){
        tagRepository.saveAll(tags);
    }


    /**
     * Record 연관된 태그 모두 저장
     */
    @Transactional
    public void saveAllTagByRecord(RecordGetReq recordGetReq, Record record) {
        List<Tag> tags = recordGetReq.getTags();
        for (Tag tag : tags) {
            record.addTagList(tag);
            tag.createMember(record.getMember());
        }
        saveTag(tags);
    }
}
