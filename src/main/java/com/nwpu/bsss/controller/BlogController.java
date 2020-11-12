package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReleaseBlogBody;
import com.nwpu.bsss.response.ArticleListResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@RestController
public class BlogController {
	
	@Resource
	private BlogService blogService;
	
	@PostMapping("/article")
	public MyResponseEntity<BlogEntity> releaseBlog(@RequestBody ReleaseBlogBody blogRequest) {
		
		BlogEntity blogEntity = new BlogEntity();
		
		blogEntity.setAuthor(blogRequest.getUserId());
		blogEntity.setCreateTime(new Timestamp(new Date().getTime()));
		blogEntity.setPlaintext(blogRequest.getContent());
		blogEntity.setTitle(blogRequest.getTitle());
		
		long id = this.blogService.createBlog(blogEntity);
		
		return new MyResponseEntity<>(Code.OK, "ok", blogEntity);
	}
	
	@GetMapping("/article")
	public MyResponseEntity<BlogEntity> findBlog(@RequestParam("articleId") int id) {
		return new MyResponseEntity<>(Code.OK, "ok", this.blogService.findByBlogID(id));
	}
	
	@GetMapping("/articleList")
	@ResponseBody
	public ArticleListResponse getArticleList() {
		// TODO: 11/12/2020  This method can't be implemented until this API is specified
		return new ArticleListResponse(null);
	}
}
