package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.CommentEntity;
import com.nwpu.bsss.domain.dto.PostBlogBody;
import com.nwpu.bsss.domain.dto.PostCommentBody;
import com.nwpu.bsss.domain.dto.PostFavBody;
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
        getBlogResponse.setLikeNum(this.likeService.getLikesNum(blogId));
        getBlogResponse.setCommentNum(this.commentService.getCommentsNum(blogId));
        getBlogResponse.setShareNum(77L);//TODO：硬编码
        getBlogResponse.setFavoriteNum(88L);

        return new MyResponseEntity<>(Code.OK, "ok", getBlogResponse);
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
                                                @RequestParam("blogId") long blogId,
                                                @RequestParam("userId") String userId,
                                                @RequestBody PostCommentBody body) {

        CommentEntity commentEntity = new CommentEntity(Long.parseLong(userId), blogId, body.getCommentId(), body.getContent());

        this.commentService.saveComment(commentEntity);
        return new MyResponseEntity<>(Code.OK, "评论发布成功", null);

    }

    @PostMapping("/blog/like")
    public MyResponseEntity<Object> likeBlog(@RequestHeader("accessToken") String accessToken,
                                             @RequestParam("blogId") long blogId,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("like") boolean like) {

        try {
            likeService.likeBlog(blogId, Long.parseLong(userId), like);
        } catch (DataIntegrityViolationException e) {//点赞的博客不存在，导致插入时导致违反数据完整性
            return new MyResponseEntity<>(Code.BAD_OPERATION, "点赞的博客不存在！", null);
        }
        return new MyResponseEntity<>(Code.OK, "ok", null);
    }

    @GetMapping("/blog/like")
    public MyResponseEntity<LikeStatusResponse> getLikeStatus(@RequestHeader("accessToken") String accessToken,
                                                              @RequestParam("userId") String userId,
                                                              @RequestParam("blogId") long blogId) {
        LikeStatusResponse likeStatusResponse = new LikeStatusResponse();
        likeStatusResponse.setStatus(false);

        boolean status = likeService.getLikeStatus(blogId, Long.parseLong(userId));
        likeStatusResponse.setStatus(status);

        return new MyResponseEntity<>(Code.OK, "ok", likeStatusResponse);

    }
}
