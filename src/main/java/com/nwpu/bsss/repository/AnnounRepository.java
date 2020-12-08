package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnounRepository extends JpaRepository<AnnouncementsEntity,Long> {

    Optional<AnnouncementsEntity> findTopByOrderByStartTimeDesc();
}
