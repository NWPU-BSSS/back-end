package com.nwpu.bsss.domain.dto;

/**
 * Created: 2020-12-07 14:17:24
 *
 * @author Zejia Lin
 */
public class PostFavBody {
	
	private String blogId;
	private String favorite;
	
	public long getBlogId() {
		return Long.parseLong(this.blogId);
	}
	
	public boolean isFavorite() {
		return Boolean.parseBoolean(this.favorite);
	}
}
