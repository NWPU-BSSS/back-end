package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface AnnounRepository extends JpaRepository<AnnouncementEntity,Long> {
    Optional<AnnouncementEntity> getTopByEndTime();
}
