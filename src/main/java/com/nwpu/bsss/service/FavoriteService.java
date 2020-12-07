package com.nwpu.bsss.service;


public interface FavoriteService {
	
	boolean isFavorite(long userId, long blogId);
	
	void addFavorite(long userId, long blogId);
	
}
