package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;

public interface BlogService {

    long createBlog(BlogEntity blogEntity);

    BlogEntity findByBlogID(long id);

}
