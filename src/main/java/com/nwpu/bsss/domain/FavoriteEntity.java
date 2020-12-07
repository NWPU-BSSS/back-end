package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Favorites")
public class FavoriteEntity {
	
	private long id;
	private long blogId;
	private long userId;
	private Timestamp time;
	
	public FavoriteEntity() {
	}
	
	public FavoriteEntity(long userId, long blogId) {
		this.userId = userId;
		this.blogId = blogId;
		this.time = new Timestamp(System.currentTimeMillis());
	}
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}
	
	@Basic(optional = false)
	@Column(name = "BlogId")
	public long getBlogId() {
		return this.blogId;
	}
	
	@Basic(optional = false)
	@Column(name = "UserId")
	public long getUserId() {
		return this.userId;
	}
	
	@Basic(optional = false)
	@Column(name = "Time")
	public Timestamp getTime() {
		return this.time;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
