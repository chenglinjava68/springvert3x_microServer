package com.solodream.spring.vertx.resp.job;

import com.solodream.spring.vertx.jpa.domain.QuestionInfoDto;
import com.solodream.spring.vertx.resp.BaseResp;

import java.util.List;

/**
 * Question对象
 *
 * @author Young
 * @since 1.0
 */

public class QuestionResponse extends BaseResp {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 当页数据
     */
    private List<QuestionInfoDto> dataList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<QuestionInfoDto> getDataList() {
        return dataList;
    }

    public void setDataList(List<QuestionInfoDto> dataList) {
        this.dataList = dataList;
    }
}