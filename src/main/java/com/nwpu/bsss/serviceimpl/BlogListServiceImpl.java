package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.FavoriteEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.FavBlogJsonBody;
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
	public List<ReBlogJsonBody> getRecomBlog(int page) {
		//page从0开始
		List<BlogEntity> blogList = this.blogRepository.findAll();
		
		List<ReBlogJsonBody> res = new ArrayList<>();
		int fromNum = Integer.min( page*15, blogList.size());//一页开始时的博客序号
		int toNum = Integer.min(fromNum+15, blogList.size());//结束的博客序号
		for (int i=fromNum; i<toNum; i++) {
			BlogEntity blog = blogList.get(i);
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

	@Override
	public List<FavBlogJsonBody> getFavsBlog(long userId) {
		List<BlogEntity> blogList = blogRepository.findFavoritesByUserId(userId);

		List<FavBlogJsonBody> res = new ArrayList<>();

		for (BlogEntity blog : blogList) {
			UserInfoEntity userInfo = userInfoRepository.findUserInfoById(blog.getAuthorId());
			res.add(FavBlogJsonBody.parseJson(blog, userInfo.getNickName()));
		}
		return res;
	}

	@Override
	public List<KeywordBlogJsonBody> getFollowedBlog(long userId) {
		int pageSize = 15;
		List<BlogEntity> blogList = blogRepository.findFollowedByUserId(userId, pageSize);
		List<KeywordBlogJsonBody> res = new ArrayList<KeywordBlogJsonBody>();
		for(BlogEntity blogEntity:blogList){
			long authorId = blogEntity.getAuthorId();
			UserInfoEntity userInfo = userInfoRepository.findUserInfoById(authorId);
			long favoriteNum = favoriteRepository.getBlogFavoritesNum(blogEntity.getId());
			long likeNum = likeRepository.getBlogLikesNum(blogEntity.getId());
			long commentNum = commentRepository.getBlogCommentsNum(blogEntity.getId());
			res.add(KeywordBlogJsonBody.parseJson(blogEntity,userInfo.getNickName(),userInfo.getAvatarUrl(),favoriteNum,likeNum,commentNum));
		}
		return res;
	}

	@Override
	public List<KeywordBlogJsonBody> getRecentBlog(long page) {
		long begin=page*15;
		List<BlogEntity> blogEntityList=this.blogRepository.findRecentBlogs(begin);
		List<KeywordBlogJsonBody> res=new ArrayList<KeywordBlogJsonBody>();
		for(BlogEntity currentBlog:blogEntityList){
			UserInfoEntity userInfo = this.userInfoRepository.findUserInfoById(currentBlog.getAuthorId());
			long favoriteNum=this.favoriteRepository.getBlogFavoritesNum(currentBlog.getId());
			long likeNum=this.likeRepository.getBlogLikesNum(currentBlog.getId());
			long commentNum=this.commentRepository.getBlogCommentsNum(currentBlog.getId());
			res.add(KeywordBlogJsonBody.parseJson(currentBlog,userInfo.getNickName(),userInfo.getAvatarUrl(),favoriteNum,likeNum,commentNum));
		}
		return res;
	}

	@Override
	public List<KeywordBlogJsonBody> getBlogsPageByUserId(long userId, int page, int pageSize) {
		int num = page * pageSize;
		List<BlogEntity> blogList = blogRepository.findBlogsPageByUserId(userId, num, pageSize);
		List<KeywordBlogJsonBody> res = new ArrayList<>();
		UserInfoEntity userInfo = userInfoRepository.findUserInfoById(userId);
		for(BlogEntity blogEntity:blogList){
			long favoriteNum = favoriteRepository.getBlogFavoritesNum(blogEntity.getId());
			long likeNum = likeRepository.getBlogLikesNum(blogEntity.getId());
			long commentNum = commentRepository.getBlogCommentsNum(blogEntity.getId());
			res.add(KeywordBlogJsonBody.parseJson(blogEntity, userInfo.getNickName(), userInfo.getAvatarUrl(), favoriteNum, likeNum, commentNum));
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
