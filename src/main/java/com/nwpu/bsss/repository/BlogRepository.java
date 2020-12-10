package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BlogEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
	
	Set<BlogEntity> findAllByAuthorId(long authorId);

	@Query(nativeQuery = true, value = "select * from Blogs where Id=?1 ")
	BlogEntity findByBlogId(long Id);

	@Query(value = "select * from Blogs where Content like %:Keyword% or Title like %:Keyword%",nativeQuery=true)
	Set<BlogEntity> findByKeyword(@Param("Keyword") String Keyword);

	@Query(nativeQuery = true, value = "select * from Blogs,Favorites where Favorites.UserId=?1 and Blogs.Id=Favorites.BlogId ")
	List<BlogEntity> findFavoritesByUserId(long userId);

	@Query(value = "select * from Blogs ORDER BY LastModifiedTime DESC, Id DESC LIMIT ?1,15", nativeQuery = true)
	List<BlogEntity> findRecentBlogs(long begin);
}
