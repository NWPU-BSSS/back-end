package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.dto.PostBlogBody;
import com.nwpu.bsss.domain.dto.ReleaseBlogBody;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.response.blog.CommentElement;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.CommentService;
import com.nwpu.bsss.service.LikeService;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private LikeService likeService;


    @GetMapping("/blog")
    public MyResponseEntity<GetBlogResponse> getBlogInfo(@RequestParam("blogId") int blogId) {

        BlogEntity blogEntity = this.blogService.findByBlogId(blogId);
        if (blogEntity == null)
            return new MyResponseEntity<>(Code.BAD_OPERATION,"没有找到博客",null);

        GetBlogResponse getBlogResponse = new GetBlogResponse();
        getBlogResponse.setTitle(blogEntity.getTitle());
        getBlogResponse.setContent(blogEntity.getContent());
        getBlogResponse.setLikesNum(this.likeService.getLikesNum(blogId));
        getBlogResponse.setCommentsNum(this.commentService.getCommentsNum(blogId));
        getBlogResponse.setShareNum(77L);//to-do
        getBlogResponse.setFavoriteNum(88L);

        return new MyResponseEntity<>(Code.OK,"ok",getBlogResponse);
    }

    @PostMapping("/blog")
    public MyResponseEntity postBlog(@RequestHeader("accessToken") String accessToken,
                                     @RequestBody PostBlogBody body) {
        UserController.token2Id.put("asdfasdfasdfzxvzx",1L);
        Long userId = UserController.token2Id.get(accessToken);
        if(userId==null)
          return new MyResponseEntity(Code.BAD_OPERATION,"token无效！！",null);

        BlogEntity blogEntity = new BlogEntity(body.getTitle(), body.getTagA(), body.getTagB(),body.getTagC(),userId,
                new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),body.getContent());

        blogService.saveBlog(blogEntity);
        return new MyResponseEntity(Code.OK,"博客发布成功",null);

    }
}
