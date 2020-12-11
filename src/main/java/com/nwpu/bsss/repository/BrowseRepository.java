package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BrowseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JiangZhe
 */
@Repository
public interface BrowseRepository extends JpaRepository<BrowseEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Browses WHERE UserId = ?1")
    List<BrowseEntity> findAllByUserId(long userId);
}
