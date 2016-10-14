package com.solodream.spring.vertx.req.client;

import com.solodream.spring.vertx.req.BaseReq;

/**
 * Created by young on 16/10/10.
 */
public class ApplyJobRequestParam extends BaseReq {

    private int userId;

    private int jobId;

    private String jobName;

    private String companyName;

    private String style;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
