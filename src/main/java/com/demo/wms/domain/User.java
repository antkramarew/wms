package com.demo.wms.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class User implements Serializable{

    public enum Role{
        SUPERUSER, ADMIN, MANAGER, USER
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "login")
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String toString() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
