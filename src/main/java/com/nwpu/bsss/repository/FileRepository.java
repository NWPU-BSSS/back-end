package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {

    Optional<FileEntity> findByUserIdAndAndFileName(long userId,String fileName);
}
