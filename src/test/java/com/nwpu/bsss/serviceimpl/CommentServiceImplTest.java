package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class CommentServiceImplTest {
	
	@Resource
	private CommentService commentService;
	
	@Test
	@Transactional
	void createComment() {
	}
	
	@Test
	void getCommentList() {
		System.out.println(this.commentService.getCommentList(1L));
	}
}