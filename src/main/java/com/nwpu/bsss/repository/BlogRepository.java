package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
	
	Set<BlogEntity> findAllByAuthorId(long authorId);

	//findBy+属性名 如 findById才可以调用JPA中的方法
	@Query(nativeQuery = true, value = "select * from Blogs where Id=?1 ")
	BlogEntity findByBlogId(long Id);


	
}
