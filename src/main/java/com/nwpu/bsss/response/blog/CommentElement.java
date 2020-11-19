package com.nwpu.bsss.response.blog;

import com.nwpu.bsss.domain.CommentEntity;

import java.util.Date;

/**
 * 这是前端要求的数组中的<b>一个元素</b>的格式，在返回时记得把它用List包起来
 */
public class CommentElement {
	
	private long userId;
	private String nickname;
	private String avatarUrl;
	private String content;
	private Date time;
	private int level;
	private long reply2UserId;
	private String reply2Nickname;
	
	public CommentElement(CommentEntity entity, String nickname, String avatarUrl, int level, long reply2UserId, String reply2Nickname) {
		this.userId = entity.getUserId();
		this.nickname = nickname;
		this.avatarUrl = avatarUrl;
		this.content = entity.getContent();
		this.time = entity.getTime();
		this.level = level;
		this.reply2UserId = reply2UserId;
		this.reply2Nickname = reply2Nickname;
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
	
	public String getAvatarUrl() {
		return this.avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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
	
	public String getReply2Nickname() {
		return this.reply2Nickname;
	}
	
	public void setReply2Nickname(String reply2Nickname) {
		this.reply2Nickname = reply2Nickname;
	}
	
	public long getReply2UserId() {
		return this.reply2UserId;
	}
	
	public void setReply2UserId(long reply2UserId) {
		this.reply2UserId = reply2UserId;
	}
	
	@Override
	public String toString() {
		return "CommentElement{" +
				"\n\tuserId=" + this.userId +
				",\n\t nickname='" + this.nickname + '\'' +
				",\n\t avatarUrl='" + this.avatarUrl + '\'' +
				",\n\t content='" + this.content + '\'' +
				",\n\t time=" + this.time +
				",\n\t level=" + this.level +
				",\n\t reply2UserId='" + this.reply2UserId + '\'' +
				",\n\t reply2Nickname='" + this.reply2Nickname + '\'' +
				"\n}\n";
	}
}
