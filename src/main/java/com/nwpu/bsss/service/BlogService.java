package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;

public interface BlogService {
	
	BlogEntity findByBlogId(long id);
	
	void saveBlog(BlogEntity blogEntity);
	
	
}
