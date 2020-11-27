package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BlogEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
class BlogRepositoryTest {
	
	@Resource
	BlogRepository blogRepository;
	
	@Test
	void findAllByAuthor() {
		Set<BlogEntity> blogSet = this.blogRepository.findAllByAuthorId(1L);
		System.out.println(blogSet);
	}
}