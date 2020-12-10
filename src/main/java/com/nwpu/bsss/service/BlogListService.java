package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.dto.FavBlogJsonBody;
import com.nwpu.bsss.domain.dto.KeywordBlogJsonBody;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created: 2020-12-07 20:53:14<br>
 *
 * @author Zejia Lin
 */
@Resource
public interface BlogListService {
	
	List<ReBlogJsonBody> getRecomBlog(int page);

	List<KeywordBlogJsonBody> getKeywordBlog(String word);

	List<FavBlogJsonBody> getFavsBlog(long userId);

	List<KeywordBlogJsonBody> getFollowedBlog(long userId);
}
