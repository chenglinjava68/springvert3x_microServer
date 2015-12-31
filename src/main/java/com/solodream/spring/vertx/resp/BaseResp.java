package com.solodream.spring.vertx.resp;

/**
 * Created by young on 15/12/31.
 */
public class BaseResp<T extends BaseResp> {
    private int code = 100;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
