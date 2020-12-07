package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author alecHe
 * @desc ...
 * @date 2020-12-07 11:10:41
 */
@Entity
@Table(name = "Announcements", schema = "BSSS")
public class AnnouncementsEntity {
    private long id;
    private String title;
    private Long publisher;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp publishTime;
    private String content;

    public AnnouncementsEntity(String title, Long publisher, Timestamp startTime, Timestamp endTime, Timestamp publishTime, String content) {
        this.title = title;
        this.publisher = publisher;
        this.startTime = startTime;
        this.endTime = endTime;
        this.publishTime = publishTime;
        this.content = content;
    }

    public AnnouncementsEntity() {

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
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Publisher")
    public Long getPublisher() {
        return publisher;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "StartTime")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "EndTime")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "PublishTime")
    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    @Basic
    @Column(name = "Content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnouncementsEntity that = (AnnouncementsEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(publishTime, that.publishTime) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publisher, startTime, endTime, publishTime, content);
    }
}
