package com.nwpu.bsss.domain;

import javax.persistence.*;

@Entity
@Table(name = "Follow", schema = "BSSS")
public class FollowEntity {
    private long id;
    private long bloggerId;
    private long userId;

    public FollowEntity(){

    }
    public FollowEntity(long bloggerId, long userId){
        this.bloggerId = bloggerId;
        this.userId = userId;
    }

    @Id
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BloggerId")
    public long getBlogerId() {
        return bloggerId;
    }

    public void setBlogerId(long blogerId) {
        this.bloggerId = blogerId;
    }

    @Basic
    @Column(name = "UserId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowEntity that = (FollowEntity) o;

        if (id != that.id) return false;
        if (bloggerId != that.bloggerId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (bloggerId ^ (bloggerId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
