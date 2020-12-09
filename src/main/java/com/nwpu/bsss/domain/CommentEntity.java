package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Comments")
public class CommentEntity implements Comparable<CommentEntity> {
	
	private long id;
	private long userId;
	private long blogId;
	private Long parentId;
	private Set<CommentEntity> children;
	private String content;
	private Timestamp time;
	
	public CommentEntity() {
		this.children = new HashSet<>();
	}
	
	public CommentEntity(long userId, long blogId, Long parentId, String content) {
		this.userId = userId;
		this.blogId = blogId;
		this.parentId = parentId;
		this.content = content;
		this.children = new HashSet<>();
		this.time = new Timestamp(System.currentTimeMillis());
	}
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Basic(optional = false)
	@Column(name = "UserId")
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Basic(optional = false)
	@Column(name = "BlogId")
	public long getBlogId() {
		return this.blogId;
	}
	
	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}
	
	/**
	 * 如果是主楼，则{@code parentId == -1}
	 */
	@Basic
	@Column(name = "CommentId")
	public Long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			orphanRemoval = true)
	@JoinColumn(name = "CommentId")
	public Set<CommentEntity> getChildren() {
		return this.children;
	}
	
	public void setChildren(Set<CommentEntity> children) {
		this.children = children;
	}
	
	@Basic(optional = false)
	@Column(name = "Content")
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic(optional = false)
	@Column(name = "Time")
	public Timestamp getTime() {
		return this.time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Override
	public int compareTo(CommentEntity o) {
		return -this.time.compareTo(o.time);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		CommentEntity that = (CommentEntity) o;
		return this.id == that.id &&
				this.userId == that.userId &&
				this.blogId == that.blogId &&
				this.parentId.equals(that.parentId) &&
				this.children.equals(that.children) &&
				this.content.equals(that.content) &&
				this.time.equals(that.time);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.userId, this.blogId, this.parentId, this.content, this.time);
	}
	
	@Override
	public String toString() {
		return "CommentEntity{\n" +
				"\tid=" + this.id + ",\n" +
				"\tuserId=" + this.userId + ",\n" +
				"\tblogId=" + this.blogId + ",\n" +
				"\tparentId=" + this.parentId + ",\n" +
				"\tcontent='" + this.content + '\'' + ",\n" +
				"\tchildren=" + this.getChildren() + ",\n" +
				"time=" + this.time + "\n" +
				"\t}\n";
	}
}
