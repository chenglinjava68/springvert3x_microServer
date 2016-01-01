package com.solodream.spring.vertx.service;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.DateUtil;
import com.solodream.spring.vertx.common.DistanceUtil;
import com.solodream.spring.vertx.jpa.domain.PoiInfoDto;
import com.solodream.spring.vertx.jpa.domain.ScheduleInfoDto;
import com.solodream.spring.vertx.mapper.PoiInfoMapper;
import com.solodream.spring.vertx.mapper.RouteInfoMapper;
import com.solodream.spring.vertx.mapper.ScheduleInfoMapper;
import com.solodream.spring.vertx.req.client.GetRouteDetailReq;
import com.solodream.spring.vertx.resp.poi.GetRouteList4JobResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by young on 16/1/1.
 */
@Service
public class SearchService {
    @Autowired
    private RouteInfoMapper routeInfoMapper;
    @Autowired
    private PoiInfoMapper poiInfoMapper;
    @Autowired
    private ScheduleInfoMapper scheduleInfoMapper;
    @Autowired
    private RedisCacheService redisCacheService;

    public GetRouteList4JobResp search(GetRouteDetailReq request) {
        GetRouteList4JobResp response = null;
        String fromId = request.getFromId();
        String toId = request.getToId();
        PoiInfoDto fromPoi = poiInfoMapper.get(Integer.parseInt(fromId));
        PoiInfoDto toPoi = poiInfoMapper.get(Integer.parseInt(toId));
        String startlat = fromPoi.getLatitude();
        String startlng = fromPoi.getLongitude();

        String endlat = toPoi.getLatitude();
        String endlng = toPoi.getLongitude();

        List<ScheduleInfoDto> jobs = scheduleInfoMapper.queryByTime(Integer.parseInt(request.getContractId()));

        for (ScheduleInfoDto job : jobs) {
            List<PoiInfoDto> pois = JSON.parseArray(job.getExtend(), PoiInfoDto.class);
            int place = 0;
            boolean mark = false;

            for (PoiInfoDto poi : pois) {
                if (DistanceUtil.checkRange(startlng, startlat, poi.getLongitude(), poi.getLatitude(), 200)) {
                    ++place;
                    mark = true;
                }
                if (DistanceUtil.checkRange(endlng, endlat, poi.getLongitude(), poi.getLatitude(), 200)) {
                    --place;
                }

            //    poi.setStationStartTime(DateUtil.addDateMinu(job.getDepartureTime(), Integer.parseInt(poi.getIntervalTime())));
            }
            //if mark ==0 && mark,this route is right
            if (place == 0 && mark) {
                int vehicleId = job.getVehicleId();

                String vehicleNO = job.getVehicleNo();
                boolean match = false;
                String currentDate = DateUtil.formatDate4YYYYMMDD();
                List<String> setStrs = null;

                String lat = "";
                String lng = "";
                String easteOrwest = "";
                String northOrsouth = "";
                String direction = "";
                String speed = "";

            }

        }
        return response;

    }
}
