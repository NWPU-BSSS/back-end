package com.nwpu.bsss.domain;

import com.nwpu.bsss.domain.dto.RegisterBody;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Users", schema = "BSSS", catalog = "")
public class UserEntity {
	private long id;
	private String userName;
	private String email;
	private String password;
	private Timestamp time;
	private long phone;
	private Collection<AccessTokensEntity> accessTokensById;
	private Collection<BlogEntity> blogsById;
	private Collection<CommentEntity> commentsById;
	private Collection<FileEntity> filesById;
	private Collection<LikeEntity> likesById;
	private Collection<UnreadMessagesEntity> unreadMessagesById;
	private UserInfoEntity userInfosById;
    private Collection<FollowEntity> followsById;
    private Collection<FollowEntity> followsById_0;
	
	public UserEntity() {

	}
	
	public UserEntity(RegisterBody registerBody) {
		this.userName = registerBody.getUsername();
		this.email = registerBody.getEmail();
		this.password = registerBody.getPassword();
		this.time = new Timestamp(new Date().getTime());
		this.phone = registerBody.getPhone();
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
	@Column(name = "UserName")
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Basic
	@Column(name = "Email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "Password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "CreateTime")
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Basic
	@Column(name = "Phone")
	public long getPhone() {
		return this.phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		UserEntity that = (UserEntity) o;
		return this.id == that.id &&
				this.phone == that.phone &&
				this.userName.equals(that.userName) &&
				this.email.equals(that.email) &&
				this.password.equals(that.password) &&
				this.time.equals(that.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.userName, this.email, this.password, this.time, this.phone);
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
	public Collection<AccessTokensEntity> getAccessTokensById() {
		return accessTokensById;
	}

	public void setAccessTokensById(Collection<AccessTokensEntity> accessTokensById) {
		this.accessTokensById = accessTokensById;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "userByAuthorId")
	public Collection<BlogEntity> getBlogsById() {
		return blogsById;
	}

	public void setBlogsById(Collection<BlogEntity> blogsById) {
		this.blogsById = blogsById;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
	public Collection<CommentEntity> getCommentsById() {
		return commentsById;
	}

	public void setCommentsById(Collection<CommentEntity> commentsById) {
		this.commentsById = commentsById;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
	public Collection<FileEntity> getFilesById() {
		return filesById;
	}

	public void setFilesById(Collection<FileEntity> filesById) {
		this.filesById = filesById;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
	public Collection<LikeEntity> getLikesById() {
		return likesById;
	}

	public void setLikesById(Collection<LikeEntity> likesById) {
		this.likesById = likesById;
	}

	@OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
	public Collection<UnreadMessagesEntity> getUnreadMessagesById() {
		return unreadMessagesById;
	}

	public void setUnreadMessagesById(Collection<UnreadMessagesEntity> unreadMessagesById) {
		this.unreadMessagesById = unreadMessagesById;
	}

	@OneToOne(cascade={CascadeType.REMOVE},mappedBy = "usersById")
	public UserInfoEntity getUserInfosById() {
		return userInfosById;
	}

	public void setUserInfosById(UserInfoEntity userInfosById) {
		this.userInfosById = userInfosById;
	}

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByBloggerId")
    public Collection<FollowEntity> getFollowsById() {
        return followsById;
    }

    public void setFollowsById(Collection<FollowEntity> followsById) {
        this.followsById = followsById;
    }

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy = "usersByUserId")
    public Collection<FollowEntity> getFollowsById_0() {
        return followsById_0;
    }

    public void setFollowsById_0(Collection<FollowEntity> followsById_0) {
        this.followsById_0 = followsById_0;
    }
}
