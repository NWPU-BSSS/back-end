package com.nwpu.bsss.serviceimpl;


import com.nwpu.bsss.domain.FavoriteEntity;
import com.nwpu.bsss.repository.FavoriteRepository;
import com.nwpu.bsss.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	@Resource
	FavoriteRepository favoriteRepository;
	
	@Override
	public boolean isFavorite(long userId, long blogId) {
		return this.favoriteRepository.existsByUserIdAndBlogId(userId, blogId);
	}
	
	@Override
	public void addFavorite(long userId, long blogId) {
		if (!this.isFavorite(userId, blogId)) {
			FavoriteEntity entity = new FavoriteEntity(userId, blogId);
			this.favoriteRepository.save(entity);
		}
	}
}
