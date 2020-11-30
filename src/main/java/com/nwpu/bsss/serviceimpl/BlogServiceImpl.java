package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.repository.UserInfoRepository;
import com.nwpu.bsss.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Resource
	private BlogRepository blogRepository;

	@Resource
	private UserInfoRepository userInfoRepository;
	
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
	
	@Override
	public List<ReBlogJsonBody> getREblog() {

		List<BlogEntity> blogList = this.blogRepository.findAll();
		long count = blogList.size();
		List<Integer> seq = this.GenerateSEQ(count);

		List<ReBlogJsonBody> res = new ArrayList<>();

		for (Integer no : seq) {
			BlogEntity blog = blogList.get(no);
			UserInfoEntity userInfo = userInfoRepository.findUserInfoById(blog.getAuthorId());

			res.add(ReBlogJsonBody.parseJson(blog,userInfo.getNickName(),userInfo.getAvatarUrl()));
		}
		return res;
	}
	
	private List<Integer> GenerateSEQ(long count) {
		List<Integer> seq = new ArrayList<>();
		Random rand = new Random();
		HashMap<Integer, Integer> map = new HashMap<>();
		//返回条数为15条
		int number = Integer.min((int) count, 15);
		while (number > 0) {
			int no = rand.nextInt((int) count);
			if (map.get(no) == null) {
				map.put(no, 1);
				seq.add(no);
				number--;
			}
		}
		return seq;
	}
}
