package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;

import java.util.List;

public interface BlogService {

    long createBlog(BlogEntity blogEntity);

    BlogEntity findByBlogId(long id);

    List<BlogEntity> findAll();

    void saveBlog(BlogEntity blogEntity);

    List<ReBlogJsonBody> getREblog();
}
