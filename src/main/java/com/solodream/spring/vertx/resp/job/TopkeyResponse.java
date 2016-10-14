package com.solodream.spring.vertx.resp.job;

import com.solodream.spring.vertx.resp.BaseResp;

/**
 * Created by young on 16/10/13.
 */
public class TopkeyResponse  extends BaseResp {


    String topkey;

    public String getTopkey() {
        return topkey;
    }

    public void setTopkey(String topkey) {
        this.topkey = topkey;
    }
}
