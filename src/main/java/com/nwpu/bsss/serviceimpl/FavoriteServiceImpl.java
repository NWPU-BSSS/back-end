package com.nwpu.bsss.serviceimpl;


import com.nwpu.bsss.domain.FavoriteEntity;
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
	public void setFavorite(long userId, long blogId, boolean isFavorite) {
		boolean existsInDb = this.isFavorite(userId, blogId);
		if (isFavorite && !existsInDb) {
			FavoriteEntity entity = new FavoriteEntity(userId, blogId);
			this.favoriteRepository.save(entity);
		} else if (!isFavorite && existsInDb) {
			this.favoriteRepository.deleteByUserIdAndBlogId(userId, blogId);
		}
	}
}
