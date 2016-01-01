package com.solodream.spring.vertx.mapper;


import com.solodream.spring.vertx.jpa.domain.ScheduleInfoDto;

import java.util.List;

/**
 * ScheduleInfoMapper
 *
 * @author Young
 * @version 1.0
 * @since 2015-09-24 00:18:16
 */
public interface ScheduleInfoMapper {

    public List<ScheduleInfoDto> queryByTime(int contractId);

}