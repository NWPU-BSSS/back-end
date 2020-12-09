package com.nwpu.bsss.service;


public interface FavoriteService {
	
	boolean isFavorite(long userId, long blogId);
	
	void setFavorite(long userId, long blogId, boolean isFavorite);

	long getFavoriteNum(long blogId);
}
