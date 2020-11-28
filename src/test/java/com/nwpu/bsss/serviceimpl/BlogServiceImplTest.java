package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BlogServiceImplTest {
	
	@Resource
	BlogService blogService;
	
	@Test
	void getREblog() {
		this.blogService.getREblog();
	}
}