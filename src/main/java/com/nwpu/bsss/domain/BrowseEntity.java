package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author JiangZhe
 */
@Entity
@Table(name = "Browses")
public class BrowseEntity {
    private long id;
    private long blogId;
    private long userId;
    private Date time;

    public BrowseEntity(){

    }

    public BrowseEntity(Long id, Long blogId, Long userId, Date time){
        this.id = id;
        this.blogId = blogId;
        this.userId = userId;
        this.time = time;
    }

    @Id
    @Column(name = "id")
    public long getId(){
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BlogId")
    public long getBlogId(){
        return this.blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    @Basic
    @Column(name = "UserId")
    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column
    public Date getTime(){
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrowseEntity that = (BrowseEntity) o;
        return id == that.id &&
                blogId == that.blogId &&
                userId == that.userId &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blogId, userId, time);
    }
}
