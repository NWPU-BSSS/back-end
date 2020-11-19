package com.nwpu.bsss.domain;


import lombok.Data;

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
	private long resume;
	private long introduction;
	private long realName;
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}
	
	@Basic(optional = false)
	@Column(name = "Nickname")
	public String getNickName() {
		return this.nickName;
	}
	
	@Basic(optional = false)
	@Column(name = "gender")
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
	public long getResume() {
		return this.resume;
	}
	
	@Basic(optional = false)
	@Column(name = "Introduction")
	public long getIntroduction() {
		return this.introduction;
	}
	
	@Basic(optional = false)
	@Column(name = "RealName")
	public long getRealName() {
		return this.realName;
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
	
	public void setResume(long resume) {
		this.resume = resume;
	}
	
	public void setIntroduction(long introduction) {
		this.introduction = introduction;
	}
	
	public void setRealName(long realName) {
		this.realName = realName;
	}
}
