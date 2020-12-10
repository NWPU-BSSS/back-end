package com.nwpu.bsss.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "Blogs", schema = "BSSS", catalog = "")
public class BlogEntity {
	private long id;
	private String title;
	private String tagA;
	private String tagB;
	private String tagC;
	private long authorId;
	private Timestamp releaseTime;
	private Timestamp lastModifiedTime;
	private String content;
	private UserEntity userByAuthorId;
	private Collection<LikeEntity> likesById;
	
	public BlogEntity(String title, String tagA, String tagB, String tagC,
					  long authorId, Timestamp releaseTime, Timestamp lastModifiedTime, String content) {
		this.title = title;
		this.tagA = tagA;
		this.tagB = tagB;
		this.tagC = tagC;
		this.authorId = authorId;
		this.releaseTime = releaseTime;
		this.lastModifiedTime = lastModifiedTime;
		this.content = content;
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
	
	@Basic
	@Column(name = "Title")
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name = "TagA")
	public String getTagA() {
		return this.tagA;
	}
	
	public void setTagA(String tagA) {
		this.tagA = tagA;
	}
	
	@Basic
	@Column(name = "TagB")
	public String getTagB() {
		return this.tagB;
	}
	
	public void setTagB(String tagB) {
		this.tagB = tagB;
	}
	
	@Basic
	@Column(name = "TagC")
	public String getTagC() {
		return this.tagC;
	}
	
	public void setTagC(String tagC) {
		this.tagC = tagC;
	}
	
	@Basic
	@Column(name = "Content")
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String plaintext) {
		this.content = plaintext;
	}
	
	@Basic
	@Column(name = "ReleaseTime")
	public Timestamp getReleaseTime() {
		return this.releaseTime;
	}
	
	public void setReleaseTime(Timestamp createTime) {
		this.releaseTime = createTime;
	}
	
	@Basic
	@Column(name = "LastModifiedTime")
	public Timestamp getLastModifiedTime() {
		return this.lastModifiedTime;
	}
	
	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	@Basic
	@Column(name = "AuthorId")
	public long getAuthorId() {
		return this.authorId;
	}
	
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		BlogEntity that = (BlogEntity) o;
		return this.id == that.id &&
				this.title.equals(that.title) &&
				this.tagA.equals(that.tagA) &&
				this.tagB.equals(that.tagB) &&
				this.tagC.equals(that.tagC) &&
				this.authorId == that.authorId &&
				this.releaseTime.equals(that.releaseTime) &&
				this.lastModifiedTime.equals(that.lastModifiedTime) &&
				this.content.equals(that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title, this.tagA, this.tagB, this.tagC, this.authorId, this.releaseTime, this.lastModifiedTime, this.content);
	}

	@Override
	public String toString() {
		return "BlogEntity{" +
				"id=" + this.id +
				", title='" + this.title + '\'' +
				", tagA='" + this.tagA + '\'' +
				", tagB='" + this.tagB + '\'' +
				", tagC='" + this.tagC + '\'' +
				", authorId=" + this.authorId +
				", releaseTime=" + this.releaseTime +
				", lastModifiedTime=" + this.lastModifiedTime +
				", content='" + this.content + '\'' +
				"\n}\n";
	}

	@ManyToOne
	@JoinColumn(name = "AuthorId", referencedColumnName = "Id",insertable = false, updatable = false)
	public UserEntity getUserByAuthorId() {
		return userByAuthorId;
	}

	public void setUserByAuthorId(UserEntity userByAuthorId) {
		this.userByAuthorId = userByAuthorId;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "blogsByBlogId")
	public Collection<LikeEntity> getLikesById() {
		return likesById;
	}

	public void setLikesById(Collection<LikeEntity> likesById) {
		this.likesById = likesById;
	}
}
