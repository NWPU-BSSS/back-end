package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;

import java.util.List;

public interface BlogService {

    long createBlog(BlogEntity blogEntity);

    BlogEntity findByBlogID(long id);

    List<BlogEntity> findAll();

}
