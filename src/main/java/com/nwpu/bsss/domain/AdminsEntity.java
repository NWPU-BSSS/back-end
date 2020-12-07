package com.nwpu.bsss.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author alecHe
 * @desc ...
 * @date 2020-12-07 11:37:10
 */
@Entity
@Table(name = "Admins", schema = "BSSS", catalog = "")
public class AdminsEntity {
    private long id;
    private String username;
    private String password;

    @Id
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminsEntity that = (AdminsEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
