package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public long createBlog(BlogEntity blogEntity) {
        return blogRepository.save(blogEntity).getId();
    }

    @Override
    public BlogEntity findByBlogID(long id) {
        return blogRepository.findBlogById(id);
    }
}
