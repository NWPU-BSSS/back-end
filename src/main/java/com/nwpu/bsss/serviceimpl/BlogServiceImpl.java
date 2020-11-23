package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class BlogServiceImpl implements BlogService {

	//实体管理器还是需要的，不然无法调用JPA内置的增删改查
	@PersistenceContext
	private EntityManager entityManager;
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

	@Override
	public List<BlogEntity> getREblog() {
		List<BlogEntity> blogList = blogRepository.findAll();
		long count = blogList.size();
		List<Integer> seq = GenerateSEQ(count);
		List<BlogEntity> res = new ArrayList<>();
		for(Integer no: seq){
			res.add(blogList.get(no));
		}
		return res;
	}

	private List<Integer> GenerateSEQ(long count) {
		List<Integer> seq = new ArrayList<>();
		Random rand = new Random();
		HashMap<Integer,Integer> map = new HashMap<>();
		//返回条数为15条
		int number = 15;
		while(number > 0){
			int no = rand.nextInt((int) count)+1;
			if(map.get(no) == null){
				map.put(no,1);
				seq.add(no);
				number--;
			}
		}
		return seq;
	}
}
