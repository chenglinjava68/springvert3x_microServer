package com.solodream.spring.vertx.jpa.model.base;

import java.io.Serializable;
import java.util.HashMap;


public class BaseBean implements Serializable {

    private static final long serialVersionUID = -54075256588666111L;

    private Integer id;

    private Integer isDeleted;

    private java.util.Date gmtCreated;

    private java.util.Date gmtModified;

    private String extend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getIsDeleted() {
        return isDeleted;
    }


    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }


    public java.util.Date getGmtCreated() {
        return gmtCreated;
    }


    public void setGmtCreated(java.util.Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }


    public java.util.Date getGmtModified() {
        return gmtModified;
    }


    public void setGmtModified(java.util.Date gmtModified) {
        this.gmtModified = gmtModified;
    }


    public String getExtend() {
        return extend;
    }


    public void setExtend(String extend) {
        this.extend = extend;
    }


    public java.util.Map<String, String> getJsonExtend() {
        try {
            return null;// JsonUtil.toMap(this.extend);
        } catch (Exception e) {
            return new HashMap<String, String>();
        }
    }

}
