package com.nwpu.bsss.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserInfos")
public class UserInfoEntity {

	public UserInfoEntity() {
		this.nickName = "yourNickName";
		this.gender = 0;
		this.studentNo = 2000000000;
		this.className = "14000000";
		this.isVerified = false;
		this.resume = "I'm a NPUer";
		this.introduction = "I love NPU";
		this.realName = "";
		this.level = 1;
		this.avatarUrl = "/avatar/default";
	}

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
	
	@Id
	@Column(name = "Id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}
	
	@Basic(optional = false)
	@Column(name = "Nickname")
	public String getNickName() {
		return this.nickName;
	}
	
	@Basic(optional = false)
	@Column(name = "Gender")
	public int getGender() {
		return this.gender;
	}
	
	@Basic(optional = false)
	@Column(name = "Sno")
	public long getStudentNo() {
		return this.studentNo;
	}
	
	@Basic(optional = false)
	@Column(name = "Class")
	public String getClassName() {
		return this.className;
	}
	
	@Basic(optional = false)
	@Column(name = "VerifyStatus")
	public boolean isVerified() {
		return this.isVerified;
	}
	
	@Basic(optional = false)
	@Column(name = "Resume")
	public String getResume() {
		return this.resume;
	}
	
	@Basic(optional = false)
	@Column(name = "Introduction")
	public String getIntroduction() {
		return this.introduction;
	}
	
	@Basic(optional = false)
	@Column(name = "RealName")
	public String getRealName() {
		return this.realName;
	}

	@Basic(optional = false)
	@Column(name = "level")
	public int getLevel(){return this.level;}

	@Basic(optional = false)
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
}
