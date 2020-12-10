package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.CommentEntity;
import com.nwpu.bsss.domain.dto.LikeBlogBody;
import com.nwpu.bsss.domain.dto.PostBlogBody;
import com.nwpu.bsss.domain.dto.PostCommentBody;
import com.nwpu.bsss.domain.dto.PostFavBody;
import com.nwpu.bsss.exceptions.FavoriteStatusException;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.GetBlogResponse;
import com.nwpu.bsss.response.LikeStatusResponse;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.blog.CommentElement;
import com.nwpu.bsss.response.blog.IsFavoriteResponse;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.CommentService;
import com.nwpu.bsss.service.FavoriteService;
import com.nwpu.bsss.service.LikeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	@Resource
	private FavoriteService favoriteService;
	
	@GetMapping("/blog/comments")
	public MyResponseEntity<List<CommentElement>> getComments(@RequestParam("blogId") String blogId) {
		long bId;
		try {
			bId = Long.parseLong(blogId);
		} catch (Exception e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "参数类型错误", null);
		}
		List<CommentElement> commentList = this.commentService.getCommentList(bId);
		return new MyResponseEntity<>(Code.OK, "ok", commentList);
	}
	
	@GetMapping("/blog")
	public MyResponseEntity<GetBlogResponse> getBlogInfo(@RequestParam("blogId") String blogId) {
		
		try {
			long blog_id = Long.parseLong(blogId);
			BlogEntity blogEntity = this.blogService.findByBlogId(blog_id);
			if (blogEntity == null) {
				return new MyResponseEntity<>(Code.BAD_OPERATION, "没有找到博客", null);
			}
			
			GetBlogResponse getBlogResponse = new GetBlogResponse();
			getBlogResponse.setTitle(blogEntity.getTitle());
			getBlogResponse.setContent(blogEntity.getContent());
			getBlogResponse.setLikeNum(this.likeService.getLikesNum(blog_id));
			getBlogResponse.setCommentNum(this.commentService.getCommentsNum(blog_id));
			getBlogResponse.setShareNum(77L);//TODO：硬编码
			getBlogResponse.setFavoriteNum(this.favoriteService.getFavoriteNum(blog_id));
			getBlogResponse.setBloggerId(blogEntity.getAuthorId());
			
			return new MyResponseEntity<>(Code.OK, "ok", getBlogResponse);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "blogId格式错误", null);
		}
		
	}
	
	@PostMapping("/blog")
	public MyResponseEntity<Object> postBlog(@RequestHeader("accessToken") String accessToken,
	                                         @RequestParam("userId") String userId,
	                                         @RequestBody PostBlogBody body) {
		
		BlogEntity blogEntity = new BlogEntity(body.getTitle(), body.getTagA(), body.getTagB(), body.getTagC(), Long.parseLong(userId),
				new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), body.getContent());
		
		this.blogService.saveBlog(blogEntity);
		return new MyResponseEntity<>(Code.OK, "博客发布成功", null);
		
	}
	
	@PostMapping("/blog/comment")
	public MyResponseEntity<Object> postComment(@RequestHeader("accessToken") String accessToken,
	                                            @RequestParam("blogId") String blogId,
	                                            @RequestParam("userId") String userId,
	                                            @RequestBody PostCommentBody body) {
		
		long bId, uId;
		Long cId;
		try {
			bId = Long.parseLong(blogId);
			uId = Long.parseLong(userId);
			cId = body.getCommentId() == 0 ? null : body.getCommentId();
		} catch (Exception e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "参数类型错误", null);
		}
		CommentEntity commentEntity = new CommentEntity(uId, bId, cId, body.getContent());
		
		this.commentService.saveComment(commentEntity);
		return new MyResponseEntity<>(Code.OK, "评论发布成功", null);
		
	}
	
	@PostMapping("/blog/like")
	public MyResponseEntity<Object> likeBlog(@RequestHeader("accessToken") String accessToken,
	                                         @RequestParam("userId") String userId,
	                                         @RequestBody LikeBlogBody body) {
		
		try {
			long user_id = Long.parseLong(userId);
			this.likeService.likeBlog(body.getBlogId(), user_id, body.isLike());
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "userId或其它参数格式错误", null);
		} catch (HttpMessageNotReadableException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Json请求参数格式错误", null);
		} catch (DataIntegrityViolationException e) {//点赞的博客不存在，导致插入时导致违反数据完整性
			return new MyResponseEntity<>(Code.BAD_OPERATION, "点赞的博客不存在！", null);
		}
		return new MyResponseEntity<>(Code.OK, "ok", null);
	}
	
	@GetMapping("/blog/like")
	public MyResponseEntity<LikeStatusResponse> getLikeStatus(@RequestHeader("accessToken") String accessToken,
	                                                          @RequestParam("userId") String userId,
	                                                          @RequestParam("blogId") long blogId) {
		try {
			long user_id = Long.parseLong(userId);
			LikeStatusResponse likeStatusResponse = new LikeStatusResponse();
			likeStatusResponse.setStatus(false);
			
			boolean status = this.likeService.getLikeStatus(blogId, user_id);
			likeStatusResponse.setStatus(status);
			return new MyResponseEntity<>(Code.OK, "ok", likeStatusResponse);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "userId格式错误", null);
		}
	}
	
	@PostMapping("/blog/fav")
	public MyResponseEntity<Object> addFavorite(@RequestHeader("accessToken") String accessToken,
	                                            @RequestParam("userId") String userId,
	                                            @RequestBody PostFavBody favBody) throws FavoriteStatusException {
		long lUserId;
		long lBlogId;
		boolean bIsFavorite;
		try {
			lUserId = Long.parseLong(userId);
			lBlogId = favBody.getBlogId();
			bIsFavorite = favBody.isFavorite();
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "userId或其他参数格式错误", null);
		}
		if (this.blogService.findByBlogId(lBlogId) == null) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "博客不存在", null);
		}
		this.favoriteService.setFavorite(lUserId, lBlogId, bIsFavorite);
		return new MyResponseEntity<>(Code.OK, "ok", null);
	}
	
	@GetMapping("/blog/fav")
	public MyResponseEntity<IsFavoriteResponse> isFavorite(@RequestHeader("accessToken") String accessToken,
	                                                       @RequestParam("userId") String userId,
	                                                       @RequestParam("blogId") String blogId) {
		long lUserId, lBlogId;
		try {
			lUserId = Long.parseLong(userId);
			lBlogId = Long.parseLong(blogId);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "userId或其他参数格式错误", null);
		}
		if (this.blogService.findByBlogId(lBlogId) == null) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "博客不存在", null);
		}
		boolean status = this.favoriteService.isFavorite(lUserId, lBlogId);
		return new MyResponseEntity<>(Code.OK, "ok", new IsFavoriteResponse(status));
	}
	
}
