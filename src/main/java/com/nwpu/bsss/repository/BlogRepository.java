package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
	
	Set<BlogEntity> findAllByAuthorId(long authorId);
	
}
