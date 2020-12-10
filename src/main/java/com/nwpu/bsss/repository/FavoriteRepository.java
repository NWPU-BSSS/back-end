package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
	
	boolean existsByUserIdAndBlogId(long userId, long blogId);
	
	void deleteByUserIdAndBlogId(long userId, long blogId);

	Set<FavoriteEntity> findAllByBlogId(long blogId);

	@Query(nativeQuery = true, value = "select count(Id) from Favorites where BlogId=?1 ")
	long getBlogFavoritesNum(long blogId);
}
