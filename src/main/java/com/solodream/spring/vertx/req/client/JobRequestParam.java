package com.solodream.spring.vertx.req.client;

import com.solodream.spring.vertx.req.BaseGetListReq;

/**
 * Created by young on 16/10/10.
 */
public class JobRequestParam extends BaseGetListReq {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
