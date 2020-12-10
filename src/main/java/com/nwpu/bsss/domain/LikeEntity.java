package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author liuziyu
 * @desc ...张弛到此一游
 * @date 2020-12-07 16:01:08
 */

@Entity
@Table(name = "Likes", schema = "BSSS", catalog = "")
public class LikeEntity {

    private long id;
    private long blogId;
    private long userId;
    private Timestamp timestamp;
    private BlogEntity blogsByBlogId;
    private UserEntity usersByUserId;

    public LikeEntity() {
    }


    public LikeEntity(long blogId, long userId, Timestamp timestamp) {

        this.blogId = blogId;
        this.userId = userId;
        this.timestamp = timestamp;
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
    @Column(name = "BlogId")
    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
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
        return timestamp;
    }

    public void setTime(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne
    @JoinColumn(name = "BlogId", referencedColumnName = "Id",insertable = false, updatable = false)
    public BlogEntity getBlogsByBlogId() {
        return blogsByBlogId;
    }

    public void setBlogsByBlogId(BlogEntity blogsByBlogId) {
        this.blogsByBlogId = blogsByBlogId;
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id", insertable = false, updatable = false)
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
