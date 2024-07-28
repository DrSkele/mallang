package com.chill.mallang.domain.area.repository;

import com.chill.mallang.domain.area.model.AreaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaLogRepository extends JpaRepository<AreaLog, Long> {

}