package com.nwpu.bsss.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author JerryChan
 * @desc ...
 * @date 2020-12-08 13:05:50
 */
@Entity
@NoArgsConstructor
@Table(name = "AccessTokens", schema = "BSSS", catalog = "")
public class AccessTokensEntity {
    private long id;
    private String accessToken;
    private long userId;
    private Timestamp time;

    public AccessTokensEntity(String accessToken, long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.time = new Timestamp(new Date().getTime());
    }

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "UserId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessTokensEntity that = (AccessTokensEntity) o;
        return id == that.id && userId == that.userId && Objects.equals(accessToken, that.accessToken) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accessToken, userId, time);
    }
}
