package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.CommentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
class CommentRepositoryTest {
	
	@Resource
	private CommentRepository repository;
	
	@Test
	@Transactional
	void findByBlogIdAndParent() {
		Set<CommentEntity> comments = this.repository.findRootCommentsByBlogId(1L);
		System.out.println("啦".repeat(20));
		for (CommentEntity i : comments) {
			System.out.println(i);
		}
		System.out.println("啦".repeat(20));
		System.out.println(this.repository.findById(2L));
		System.out.println("啦".repeat(20));
		System.out.println(this.repository.findById(3L));
	}
}


