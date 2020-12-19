package com.nwpu.bsss.serviceimpl;


import com.nwpu.bsss.domain.FavoriteEntity;
import com.nwpu.bsss.exceptions.FavoriteStatusException;
import com.nwpu.bsss.repository.FavoriteRepository;
import com.nwpu.bsss.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
	
	@Resource
	FavoriteRepository favoriteRepository;
	
	@Override
	public boolean isFavorite(long userId, long blogId) {
		return this.favoriteRepository.existsByUserIdAndBlogId(userId, blogId);
	}
	
	@Override
	public void setFavorite(long userId, long blogId, boolean isFavorite) throws FavoriteStatusException {
		boolean existsInDb = this.isFavorite(userId, blogId);
		if (isFavorite) {
			if (!existsInDb) {
				FavoriteEntity entity = new FavoriteEntity(userId, blogId);
				this.favoriteRepository.save(entity);
			} else {
				throw new FavoriteStatusException("不可以收藏已收藏的博客");
			}
		} else if (existsInDb) {
			this.favoriteRepository.deleteByUserIdAndBlogId(userId, blogId);
		} else {
			throw new FavoriteStatusException("不可以取消收藏未收藏的博客");
		}
	}
	
	@Override
	public long getFavoriteNum(long blogId) {
		return this.favoriteRepository.getBlogFavoritesNum(blogId);
	}
}
