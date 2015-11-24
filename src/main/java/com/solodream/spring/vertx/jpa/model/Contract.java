package com.solodream.spring.vertx.jpa.model;

import java.util.Date;

/**
 * Created by young on 15/11/18.
 */
public class Contract {
    private String name;
    private Integer id;
    private Date createDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
