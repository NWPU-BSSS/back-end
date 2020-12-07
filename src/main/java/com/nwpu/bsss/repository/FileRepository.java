package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity,Long> {

    Optional<FileEntity> findByUserIdAndAndFileName(long userId,String fileName);
}
