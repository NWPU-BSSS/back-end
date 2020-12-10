package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author alecHe
 * @desc ...
 * @date 2020-12-10 14:46:15
 */
@Entity
@Table(name = "UnreadMessages", schema = "BSSS")
public class UnreadMessagesEntity {
    private long id;
    private Integer announcement;
    private Integer follow;
    private Integer message;
    private Integer reply;
    private Integer notice;
    private Integer like;
    private Integer comment;
    private UserEntity usersByUserId;

    @Id
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Announcement")
    public Integer getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Integer announcement) {
        this.announcement = announcement;
    }

    @Basic
    @Column(name = "Follow")
    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    @Basic
    @Column(name = "Message")
    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    @Basic
    @Column(name = "Reply")
    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    @Basic
    @Column(name = "Notice")
    public Integer getNotice() {
        return notice;
    }

    public void setNotice(Integer notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "Like")
    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    @Basic
    @Column(name = "Comment")
    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnreadMessagesEntity that = (UnreadMessagesEntity) o;
        return id == that.id &&
                Objects.equals(announcement, that.announcement) &&
                Objects.equals(follow, that.follow) &&
                Objects.equals(message, that.message) &&
                Objects.equals(reply, that.reply) &&
                Objects.equals(notice, that.notice) &&
                Objects.equals(like, that.like) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, announcement, follow, message, reply, notice, like, comment);
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false,insertable = false, updatable = false)
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
