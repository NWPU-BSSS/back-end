package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReleaseBlogBody;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.response.blog.CommentList;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class BlogController {
	
	@Resource
	private BlogService blogService;
	@Resource
	private UserService userService;
	
	@GetMapping("/comments")
	public MyResponseEntity<CommentList> getComments(@RequestParam("blogId") String blogId) {
		CommentList commentList = new CommentList();
		
		return new MyResponseEntity<>(Code.OK, "ok", commentList);
	}
	
	@PostMapping("/article")
	public MyResponseEntity<PostArticleResponse> releaseBlog(@RequestBody ReleaseBlogBody blogRequest) {
		
		BlogEntity blogEntity = new BlogEntity();
		
		blogEntity.setAuthor(blogRequest.getUserId());
		blogEntity.setCreateTime(new Timestamp(new Date().getTime()));
		blogEntity.setPlaintext(blogRequest.getContent());
		blogEntity.setTitle(blogRequest.getTitle());
		
		long id = this.blogService.createBlog(blogEntity);
		PostArticleResponse postArticleResponse = new PostArticleResponse();
		postArticleResponse.setArticleId(id);
		
		return new MyResponseEntity<>(Code.OK, "ok", postArticleResponse);
	}
	
	@GetMapping("/article")
	public MyResponseEntity<GetArticleResponse> findBlog(@RequestParam("articleId") int id) {
		BlogEntity blogEntity = this.blogService.findByBlogID(id);
		try {
			GetArticleResponse getArticleResponse = new GetArticleResponse();
			long authorId = blogEntity.getAuthor();
			String email = "匿名";
			try {
				email = this.userService.findByUserID(authorId).getEmail();
			} catch (NullPointerException ignore) {
			}
			getArticleResponse.setAuthor(email);
			getArticleResponse.setContent(blogEntity.getPlaintext());
			getArticleResponse.setTitle(blogEntity.getTitle());
			getArticleResponse.setTime(blogEntity.getCreateTime().toString());
			return new MyResponseEntity<>(Code.OK, "ok", getArticleResponse);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "找不到该博客", null);
		}
	}
	
	@GetMapping("/articleList")
	@ResponseBody
	public MyResponseEntity<List<ArticleListResponse.ArticleBrief>> getArticleList() {
		List<BlogEntity> blogEntityList = this.blogService.findAll();
		ArticleListResponse articleListResponse = new ArticleListResponse();
		for (BlogEntity b : blogEntityList) {
			ArticleListResponse.ArticleBrief articleBrief = new ArticleListResponse.ArticleBrief();
			articleBrief.setArticleId(b.getId());
			articleBrief.setTitle(b.getTitle());
			articleListResponse.getArticles().add(articleBrief);
		}
		return new MyResponseEntity<>(Code.OK, "ok", articleListResponse.getArticles());
	}
}
