package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Resource
	private BlogRepository blogRepository;
	
	@Override
	@Transactional
	public long createBlog(BlogEntity blogEntity) {
		return this.blogRepository.save(blogEntity).getId();
	}
	
	@Override
	public BlogEntity findByBlogId(long id) {
		return this.blogRepository.findByBlogId(id);
	}
	
	@Override
	public List<BlogEntity> findAll() {
		return this.blogRepository.findAll();
	}

	@Override
	public void saveBlog(BlogEntity blogEntity) {
		this.blogRepository.save(blogEntity);
	}
}
