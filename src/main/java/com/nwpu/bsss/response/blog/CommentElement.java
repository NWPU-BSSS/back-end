package com.nwpu.bsss.response.blog;

import com.nwpu.bsss.domain.CommentEntity;

import java.util.Date;

/**
 * 这是前端要求的数组中的<b>一个元素</b>的格式，在返回时记得把它用List包起来
 */
public class CommentElement {
	
	private long id;
	private long userId;
	private String nickname;
	private String avatar;
	private String content;
	private Date time;
	private int level;
	private long reply2userId;
	private String reply2nickname;
	
	public CommentElement(CommentEntity entity, String nickname, String avatarUrl, int level, long reply2userId, String reply2nickname) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.nickname = nickname;
		this.avatar = avatarUrl;
		this.content = entity.getContent();
		this.time = entity.getTime();
		this.level = level;
		this.reply2userId = reply2userId;
		this.reply2nickname = reply2nickname;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getAvatar() {
		return this.avatar;
	}
	
	public void setAvatar(String avatarUrl) {
		this.avatar = avatarUrl;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getTime() {
		return this.time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getReply2nickname() {
		return this.reply2nickname;
	}
	
	public void setReply2nickname(String reply2nickname) {
		this.reply2nickname = reply2nickname;
	}
	
	public long getReply2userId() {
		return this.reply2userId;
	}
	
	public void setReply2userId(long reply2userId) {
		this.reply2userId = reply2userId;
	}
	
	@Override
	public String toString() {
		return "CommentElement{" +
				"\n\tuserId=" + this.userId +
				",\n\t nickname='" + this.nickname + '\'' +
				",\n\t avatar='" + this.avatar + '\'' +
				",\n\t content='" + this.content + '\'' +
				",\n\t time=" + this.time +
				",\n\t level=" + this.level +
				",\n\t reply2userId='" + this.reply2userId + '\'' +
				",\n\t reply2nickname='" + this.reply2nickname + '\'' +
				"\n}\n";
	}
}
