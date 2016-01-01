package com.solodream.spring.vertx.req.client;

import com.solodream.spring.vertx.req.BaseGetListReq;

public class GetRouteDetailReq extends BaseGetListReq {


    private String contractId;

    private String customerId;

    public String getStartlng() {
        return startlng;
    }

    public void setStartlng(String startlng) {
        this.startlng = startlng;
    }

    public String getStartlat() {
        return startlat;
    }

    public void setStartlat(String startlat) {
        this.startlat = startlat;
    }

    private String keyword;


    private java.lang.String startlng;

    private java.lang.String startlat;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
