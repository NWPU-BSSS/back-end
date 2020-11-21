package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReleaseBlogBody;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.response.blog.CommentElement;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.CommentService;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
	
	@Resource
	private BlogService blogService;
	@Resource
	private UserService userService;
	@Resource
	private CommentService commentService;
	
	@GetMapping("/comments")
	public MyResponseEntity<List<CommentElement>> getComments(@RequestParam("blogId") long blogId) {
		List<CommentElement> commentList = this.commentService.getCommentList(blogId);
		return new MyResponseEntity<>(Code.OK, "ok", commentList);
	}
	
	@PostMapping("/article")
	public MyResponseEntity<PostArticleResponse> releaseBlog(@RequestBody ReleaseBlogBody blogRequest) {
		
		BlogEntity blogEntity = new BlogEntity();
		
		blogEntity.setAuthorId(blogRequest.getUserId());
		blogEntity.setReleaseTime(new Timestamp(new Date().getTime()));
		blogEntity.setContent(blogRequest.getContent());
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
			long authorId = blogEntity.getAuthorId();
			String email = "匿名";
			try {
				email = this.userService.findByUserID(authorId).getEmail();
			} catch (NullPointerException ignore) {
			}
			getArticleResponse.setAuthor(email);
			getArticleResponse.setContent(blogEntity.getContent());
			getArticleResponse.setTitle(blogEntity.getTitle());
			getArticleResponse.setTime(blogEntity.getReleaseTime().toString());
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
