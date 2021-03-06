package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	
	@Query(nativeQuery = true, value = "select * from Comments where BlogId=?1 and CommentId IS NULL")
	Set<CommentEntity> findRootCommentsByBlogId(long blogId);

	@Query(nativeQuery = true, value = "select count(Id) from Comments where BlogId=?1 and CommentId IS NULL")
	long getBlogCommentsNum(long blogId);

	Set<CommentEntity> findAllByUserId(long userId);


}
