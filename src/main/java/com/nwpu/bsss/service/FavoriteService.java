package com.nwpu.bsss.service;


import com.nwpu.bsss.exceptions.FavoriteStatusException;

public interface FavoriteService {
	
	boolean isFavorite(long userId, long blogId);
	
	void setFavorite(long userId, long blogId, boolean isFavorite) throws FavoriteStatusException;
	
	long getFavoriteNum(long blogId);
}
