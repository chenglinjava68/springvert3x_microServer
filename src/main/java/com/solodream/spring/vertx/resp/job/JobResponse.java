package com.solodream.spring.vertx.resp.job;

import com.solodream.spring.vertx.jpa.domain.JobInfoDto;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.poi.GetRouteList4JobResp;

import java.util.List;

/**
 * Created by young on 16/10/9.
 */
public class JobResponse extends BaseResp {

    /**
     * 总数
     */
    private Integer total;

    /**
     * 当页数据
     */
    private List<JobInfoDto> dataList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<JobInfoDto> getDataList() {
        return dataList;
    }

    public void setDataList(List<JobInfoDto> dataList) {
        this.dataList = dataList;
    }
}
