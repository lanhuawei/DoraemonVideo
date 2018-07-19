package com.lightsky.video.module.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Ivan.L on 2018/6/29.
 * 数据库实例
 */
@Entity
public class UserInfoBean {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @Property(nameInDb = "USER_NAME")
    private String username;
    @Property(nameInDb = "TOKEN")
    private String token;

    @Generated(hash = 131892248)
    public UserInfoBean(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}
