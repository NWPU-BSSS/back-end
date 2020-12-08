package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.FavoriteEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.KeywordBlogJsonBody;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.repository.*;
import com.nwpu.bsss.service.BlogListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created: 2020-12-07 20:54:03<b>
 *
 * @author Zejia Lin
 */
@Service
public class BlogListServiceImpl implements BlogListService {
	
	@Resource
	private BlogRepository blogRepository;
	@Resource
	private UserInfoRepository userInfoRepository;
	@Resource
	private CommentRepository commentRepository;
	@Resource
	private FavoriteRepository favoriteRepository;
	@Resource
	private LikeRepository likeRepository;

	@Override
	public List<ReBlogJsonBody> getREblog() {
		
		List<BlogEntity> blogList = this.blogRepository.findAll();
		long count = blogList.size();
		List<Integer> seq = this.GenerateSEQ(count);
		
		List<ReBlogJsonBody> res = new ArrayList<>();
		
		for (Integer no : seq) {
			BlogEntity blog = blogList.get(no);
			UserInfoEntity userInfo = this.userInfoRepository.findUserInfoById(blog.getAuthorId());
			
			res.add(ReBlogJsonBody.parseJson(blog, userInfo.getNickName(), userInfo.getAvatarUrl()));
		}
		return res;
	}

	@Override
	public List<KeywordBlogJsonBody> getKeywordBlog(String word) {
		List<BlogEntity> keywordBlogEntities=new ArrayList<BlogEntity>();
		keywordBlogEntities.addAll(this.blogRepository.findByKeyword(word));
		List<KeywordBlogJsonBody> res=new ArrayList<KeywordBlogJsonBody>();
		for(BlogEntity currentBlog:keywordBlogEntities){
			UserInfoEntity userInfo = this.userInfoRepository.findUserInfoById(currentBlog.getAuthorId());
			long favoriteNum=this.favoriteRepository.getBlogFavoritesNum(currentBlog.getId());
			long likeNum=this.likeRepository.getBlogLikesNum(currentBlog.getId());
			long commentNum=this.commentRepository.getBlogCommentsNum(currentBlog.getId());
			res.add(KeywordBlogJsonBody.parseJson(currentBlog,userInfo.getNickName(),userInfo.getAvatarUrl(),favoriteNum,likeNum,commentNum));
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
