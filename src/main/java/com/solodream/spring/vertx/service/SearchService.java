package com.solodream.spring.vertx.service;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.DateUtil;
import com.solodream.spring.vertx.common.DistanceUtil;
import com.solodream.spring.vertx.jpa.domain.PoiInfoDto;
import com.solodream.spring.vertx.jpa.domain.ScheduleInfoDto;
import com.solodream.spring.vertx.mapper.PoiInfoMapper;
import com.solodream.spring.vertx.mapper.ScheduleInfoMapper;
import com.solodream.spring.vertx.req.client.GetRouteDetailReq;
import com.solodream.spring.vertx.resp.poi.GetRouteList4JobResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by young on 16/1/1.
 */
@Service
public class SearchService {
    @Autowired
    private PoiInfoMapper poiInfoMapper;
    @Autowired
    private ScheduleInfoMapper scheduleInfoMapper;
    @Autowired
    private RedisCacheService redisCacheService;

    public GetRouteList4JobResp search(GetRouteDetailReq request) {
        GetRouteList4JobResp response = new GetRouteList4JobResp();
        List<GetRouteList4JobResp.Route4JobInfo> datalist = new ArrayList<GetRouteList4JobResp.Route4JobInfo>();
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

            int index = 0;
            for (PoiInfoDto poi : pois) {
                if (DistanceUtil.checkRange(startlng, startlat, poi.getLongitude(), poi.getLatitude(), 200)) {
                    ++place;
                    mark = true;
                }
                if (DistanceUtil.checkRange(endlng, endlat, poi.getLongitude(), poi.getLatitude(), 200)) {
                    --place;
                }
                index++;

                poi.setStationStartTime(DateUtil.addDateMinu(job.getDepartureTime(), Integer.parseInt(poi.getIntervalTime())));
            }
            //if mark ==0 && mark,this route is right
            if (place == 0 && mark) {
                int vehicleId = job.getVehicleId();

                String vehicleNO = job.getVehicleNo();
                boolean match = false;
                String currentDate = DateUtil.formatDate4YYYYMMDD();
                String currentPoi = redisCacheService.get("JOB_" + currentDate + "_" + job.getId());
                if (StringUtils.isNotBlank(currentPoi)) {
//该路线还未过站
                    if (Integer.parseInt(currentPoi.split(">")[1]) < index) {
                        String[] infos = currentPoi.split(",");

                        String name = infos[0];
                        String postCode = infos[1];
                        String lng = infos[2];
                        String lat = infos[3];
                        String direction = infos[4];
                        String speed = infos[5].split("=")[0];

                        GetRouteList4JobResp.Route4JobInfo route4JobInfo = new GetRouteList4JobResp.Route4JobInfo();
                        route4JobInfo.setDepartureTime(DateUtil.formatDateTime(job.getDepartureTime()));
                        route4JobInfo.setDirection(direction);
                        route4JobInfo.setLongitude(lng);
                        route4JobInfo.setLatitude(lat);
                        route4JobInfo.setDriverId(job.getDriverId());
                        route4JobInfo.setDriverName(job.getDriverName());
                        route4JobInfo.setDriverPhone(job.getDriverMobile());
                        route4JobInfo.setPlateNo(job.getVehicleNo());
                        route4JobInfo.setVehicleId(job.getVehicleId() + "");
                        route4JobInfo.setRouteId(job.getRouteId());
                        route4JobInfo.setRouteRemark(job.getRouteName());
                        route4JobInfo.setSpeed(speed);
                        datalist.add(route4JobInfo);
                    }
                }
//A->B->C   A,B
                else {
                    //  route
                    GetRouteList4JobResp.Route4JobInfo jobInfo = new GetRouteList4JobResp.Route4JobInfo();
                    jobInfo.setDriverId(job.getDriverId());
                    jobInfo.setDriverName(job.getDriverName());
                    jobInfo.setDriverPhone(job.getDriverMobile());
                    //if timestamp > 30,we don't need lat & lng  from station

                    pois.forEach((PoiInfoDto poi) -> {
//                    for (PoiInfoDto poi : pois) {
                        if (DistanceUtil.checkRange(poi.getLongitude(), poi.getLatitude(), startlng, startlat, 200)) {
                            if (DateUtil.getOffsetMinutes(poi.getStationStartTime(), new Date()) < 30) {
                                jobInfo.setLatitude(poi.getLatitude());
                                jobInfo.setLongitude(poi.getLongitude());
//                                jobInfo.setDirection(direction);
//                                jobInfo.setSpeed(speed);
                            }
                        }
                    });
                    jobInfo.setDriverPhone(job.getDriverMobile());
                    jobInfo.setDriverName(job.getDriverName());
                    jobInfo.setDriverId(job.getDriverId());
                    jobInfo.setRouteRemark(job.getRouteName());
                    jobInfo.setRouteId(job.getRouteId());
                    jobInfo.setVehicleId(vehicleId + "");
                    jobInfo.setPlateNo(vehicleNO);
                    jobInfo.setDepartureTime(DateUtil.formatTime(job.getDepartureTime()));
                    datalist.add(jobInfo);
                }
            }
        }

        response.setDataList(datalist);
        response.setTotal(datalist.size());
        return response;

    }
}
