package com.solodream.spring.vertx.req.client;

import com.solodream.spring.vertx.req.BaseReq;

/**
 * Created by young on 16/10/10.
 */
public class UserCommonRequestParam  extends BaseReq {

    private int userId;

    private String userMobile;

    private String userName;

    private String source;

    private int resumeId;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
