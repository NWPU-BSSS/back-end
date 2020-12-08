package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Resource
	private BlogRepository blogRepository;
	
	@Override
	public BlogEntity findByBlogId(long id) {
		return this.blogRepository.findByBlogId(id);
	}
	
	@Override
	public void saveBlog(BlogEntity blogEntity) {
		this.blogRepository.save(blogEntity);
	}
	
}
