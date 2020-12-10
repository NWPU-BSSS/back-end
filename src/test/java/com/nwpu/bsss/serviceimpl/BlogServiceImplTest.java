package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.service.BlogListService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BlogServiceImplTest {
	
	@Resource
	BlogListService blogService;

}