package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.CommentEntity;
import com.nwpu.bsss.domain.dto.PostBlogBody;
import com.nwpu.bsss.domain.dto.PostCommentBody;
import com.nwpu.bsss.domain.dto.PostBlogBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.GetBlogResponse;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.blog.CommentElement;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.CommentService;
import com.nwpu.bsss.service.LikeService;
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
	private CommentService commentService;
	@Resource
	private LikeService likeService;
	
	@GetMapping("/blog/comments")
	public MyResponseEntity<List<CommentElement>> getComments(@RequestParam("blogId") long blogId) {
		List<CommentElement> commentList = this.commentService.getCommentList(blogId);
		return new MyResponseEntity<>(Code.OK, "ok", commentList);
	}
	
	@GetMapping("/blog")
	public MyResponseEntity<GetBlogResponse> getBlogInfo(@RequestParam("blogId") int blogId) {
		
		BlogEntity blogEntity = this.blogService.findByBlogId(blogId);
		if (blogEntity == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "没有找到博客", null);
		}
		
		GetBlogResponse getBlogResponse = new GetBlogResponse();
		getBlogResponse.setTitle(blogEntity.getTitle());
		getBlogResponse.setContent(blogEntity.getContent());
		getBlogResponse.setLikesNum(this.likeService.getLikesNum(blogId));
		getBlogResponse.setCommentsNum(this.commentService.getCommentsNum(blogId));
		getBlogResponse.setShareNum(77L);//to-do
		getBlogResponse.setFavoriteNum(88L);
		
		return new MyResponseEntity<>(Code.OK, "ok", getBlogResponse);
	}
	
	@PostMapping("/blog")
	public MyResponseEntity<Object> postBlog(@RequestHeader("accessToken") String accessToken,
	                                 @RequestBody PostBlogBody body) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
		}
		
		BlogEntity blogEntity = new BlogEntity(body.getTitle(), body.getTagA(), body.getTagB(), body.getTagC(), userId,
				new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), body.getContent());
		
		this.blogService.saveBlog(blogEntity);
		return new MyResponseEntity<>(Code.OK, "博客发布成功", null);
		
	}

	@PostMapping("/blog/comment")
	public MyResponseEntity postComment(@RequestHeader("accessToken") String accessToken,
									 @RequestParam("blogId") long blogId,
									 @RequestBody PostCommentBody body) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity(Code.BAD_OPERATION, "token无效", null);
		}

		CommentEntity commentEntity=new CommentEntity(userId,blogId,body.getCommentId(),body.getContent());

		this.commentService.saveComment(commentEntity);
		return new MyResponseEntity(Code.OK, "博客发布成功", null);

	}
}
