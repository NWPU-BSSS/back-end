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
	private CommentEntity parent;
	private Set<CommentEntity> children;
	private String content;
	private Timestamp time;
	
	public CommentEntity() {
		this.children = new HashSet<>();
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CommentId")
	public CommentEntity getParent() {
		return this.parent;
	}
	
	public void setParent(CommentEntity parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			orphanRemoval = true)
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
				this.parent.equals(that.parent) &&
				this.children.equals(that.children) &&
				this.content.equals(that.content) &&
				this.time.equals(that.time);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.userId, this.blogId, this.parent, this.content, this.time);
	}
	
	@Override
	public String toString() {
		long parentId = -1;
		if (this.parent != null) {
			parentId = this.parent.id;
		}
		int level = 0;
		CommentEntity p = this.parent;
		while (p != null) {
			p = p.parent;
			++level;
		}
		return "\t".repeat(level) + "CommentEntity{" +
				"id=" + this.id +
				", userId=" + this.userId +
				", blogId=" + this.blogId +
				", parentId=" + parentId +
				", children=" + this.getChildren() +
				", content='" + this.content + '\'' +
				", time=" + this.time +
				"}\n";
	}
}
