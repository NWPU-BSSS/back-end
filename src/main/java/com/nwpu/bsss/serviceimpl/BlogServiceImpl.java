package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.Tag;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.response.BloggerTagResponse;
import com.nwpu.bsss.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

	@Override
	public BloggerTagResponse getTags(Long bloggerId){
		BloggerTagResponse bloggerTagResponse = new BloggerTagResponse();
		HashMap<String, Tag> tagList = new HashMap<String,Tag>();
		Set<BlogEntity> blogList = new HashSet<BlogEntity>();
		blogList = blogRepository.findAllByAuthorId(bloggerId);
		for(BlogEntity blog :blogList){
			if(tagList.containsKey(blog.getTagA())){//already has the tag in list, cnt++
				Tag tag = tagList.get(blog.getTagA());
				long count = tag.getCount();
				count++;
				tag.setCount(count);
				tagList.remove(blog.getTagA());
				tagList.put(blog.getTagA(),tag);
			}
			else{//add a new tag to list
				if(!StringUtils.isBlank(blog.getTagA())) {//not a empty string
					Tag tag = new Tag();
					tag.setTag(blog.getTagA());
					tag.setCount(1);
					tagList.put(blog.getTagA(), tag);
				}
			}
			if(tagList.containsKey(blog.getTagB())){
				Tag tag = tagList.get(blog.getTagB());
				long count = tag.getCount();
				count++;
				tag.setCount(count);
				tagList.remove(blog.getTagB());
				tagList.put(blog.getTagB(), tag);
			}
			else{
				if(!StringUtils.isBlank(blog.getTagB())) {
					Tag tag = new Tag();
					tag.setTag(blog.getTagB());
					tag.setCount(1);
					tagList.put(blog.getTagB(), tag);
				}
			}
			if(tagList.containsKey(blog.getTagC())){
				Tag tag = tagList.get(blog.getTagC());
				long count = tag.getCount();
				count++;
				tag.setCount(count);
				tagList.remove(blog.getTagC());
				tagList.put(blog.getTagC(), tag);
			}
			else{
				if(!StringUtils.isBlank(blog.getTagC())) {
					Tag tag = new Tag();
					tag.setTag(blog.getTagC());
					tag.setCount(1);
					tagList.put(blog.getTagC(), tag);
				}
			}

		}
		bloggerTagResponse.setTagList(tagList);
		return bloggerTagResponse;
	}
	
}
