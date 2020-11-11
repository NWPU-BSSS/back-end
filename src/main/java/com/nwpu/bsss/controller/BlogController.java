package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReleaseBlogBody;
import com.nwpu.bsss.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@RestController
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("/article")
    public ResponseEntity<BlogEntity> releaseBlog(@RequestBody ReleaseBlogBody blogRequest) {

        BlogEntity blogEntity = new BlogEntity();

        blogEntity.setAuthor(blogRequest.getUserId());
        blogEntity.setCreateTime(new Timestamp(new Date().getTime()));
        blogEntity.setPlaintext(blogRequest.getContent());
        blogEntity.setTitle(blogRequest.getTitle());

        long id =  blogService.createBlog(blogEntity);

        return new ResponseEntity<>(blogEntity, HttpStatus.CREATED);
    }

    @GetMapping("/article")
    public ResponseEntity<BlogEntity> findBlog(@RequestParam("articleId") int id) {
        return new ResponseEntity<>(blogService.findByBlogID(id),HttpStatus.OK);
    }


}
