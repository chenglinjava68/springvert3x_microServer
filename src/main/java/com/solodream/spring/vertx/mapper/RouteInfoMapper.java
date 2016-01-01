package com.solodream.spring.vertx.mapper;

import com.solodream.spring.vertx.jpa.domain.RouteContractInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RouteInfoMapper {

    public int insert(RouteContractInfoDto dto);

    public void update(@Param("routeInfo") RouteContractInfoDto dto);

    public void delete(int id);

    public void deletes(List<Integer> id);

    public void deleteRouteByCustomer(List<Integer> id);

    public RouteContractInfoDto get(int id);

    public List<RouteContractInfoDto> gets(List<Integer> id);

    public RouteContractInfoDto query(@Param("routeInfo") RouteContractInfoDto dto, @Param("keyword") String keyword);

    public List<RouteContractInfoDto> querys(@Param("routeInfo") RouteContractInfoDto dto, @Param("keyword") String keyword);

    public int count(@Param("routeInfo") RouteContractInfoDto dto, @Param("keyword") String keyword);

    public List<RouteContractInfoDto> list(@Param("routeInfo") RouteContractInfoDto dto, @Param("keyword") String keyword, @Param("offset") int offset,
                                   @Param("length") int length);

    public List<RouteContractInfoDto> listAll();

}