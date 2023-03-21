package com.todaysgym.todaysgym.record;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> getByRecordId(Long recordId);

}
