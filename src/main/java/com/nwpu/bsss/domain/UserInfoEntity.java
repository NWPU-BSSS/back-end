package com.nwpu.bsss.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserInfos")
public class UserInfoEntity {

	private long id;
	private String nickName;
	private int gender;
	private long studentNo;
	private String className;
	private boolean isVerified;
	private String resume;
	private String introduction;
	private String realName;
	private int level;
	private String avatarUrl;
	private UserEntity usersById;

	public UserInfoEntity(){
		gender = 3;//unknown
		level = 1;
		isVerified = false;
		studentNo=2018303188;//TODO:这里写死学号是因为API没有修改学号的选项
	}

	@Id
	@Column(name = "Id")
	public long getId() {
		return this.id;
	}

	@Column(name = "Nickname")
	public String getNickName() {
		return this.nickName;
	}

	@Column(name = "Gender")
	public int getGender() {
		return this.gender;
	}

	@Column(name = "Sno")
	public long getStudentNo() {
		return this.studentNo;
	}

	@Column(name = "Class")
	public String getClassName() {
		return this.className;
	}

	@Column(name = "VerifyStatus")
	public boolean isVerified() {
		return this.isVerified;
	}

	@Column(name = "Resume")
	public String getResume() {
		return this.resume;
	}

	@Column(name = "Introduction")
	public String getIntroduction() {
		return this.introduction;
	}

	@Column(name = "RealName")
	public String getRealName() {
		return this.realName;
	}

	@Column(name = "level")
	public int getLevel(){return this.level;}

	@Column(name = "Avatar")
	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setStudentNo(long studentNo) {
		this.studentNo = studentNo;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setVerified(boolean verified) {
		this.isVerified = verified;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setLevel(int level) {this.level = level; }

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@OneToOne
	@JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false)
	public UserEntity getUsersById() {
		return usersById;
	}

	public void setUsersById(UserEntity usersById) {
		this.usersById = usersById;
	}
}
