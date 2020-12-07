package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
	
	boolean existsByUserIdAndBlogId(long userId, long blogId);
	
	void deleteByUserIdAndBlogId(long userId, long blogId);
}
