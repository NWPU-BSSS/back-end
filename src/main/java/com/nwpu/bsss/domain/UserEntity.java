package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class UserEntity {
	private long id;
	private String userName;
	private String email;
	private String password;
	private Timestamp time;
	private long phone;
	
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
}
