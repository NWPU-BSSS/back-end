package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Follow", schema = "BSSS")
public class FollowEntity {
    private long id;
    private long bloggerId;
    private long userId;

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
        return id == that.id &&
                bloggerId == that.bloggerId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bloggerId, userId);
    }
}
