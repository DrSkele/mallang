package com.chill.mallang.domain.area.repository;

import com.chill.mallang.domain.area.model.AreaLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaLogRepository extends JpaRepository<AreaLog, Long> {
    List<AreaLog> findByAreaId(Long areaId);
}