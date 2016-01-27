package com.wqdata.recycler.demo;

import java.util.Date;

/**
 * Created by wangjia on 2016/1/27.
 */
public class DataBean {
    private int id;
    private String name;
    private Date createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
