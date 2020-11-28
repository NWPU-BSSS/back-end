package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface AnnounRepository extends JpaRepository<AnnouncementEntity,Long> {
    Optional<AnnouncementEntity> getAllById(Long id);
}
