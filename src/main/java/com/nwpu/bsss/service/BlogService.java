package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.response.BloggerTagResponse;

public interface BlogService {
	
	BlogEntity findByBlogId(long id);
	
	void saveBlog(BlogEntity blogEntity);

	BloggerTagResponse getTags(Long bloggerId);
	
}
