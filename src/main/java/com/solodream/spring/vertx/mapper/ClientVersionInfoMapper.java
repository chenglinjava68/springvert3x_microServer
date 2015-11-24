package com.solodream.spring.vertx.mapper;

import com.solodream.spring.vertx.jpa.domain.ClientVersionInfoDto;
import org.apache.ibatis.annotations.Param;

public interface ClientVersionInfoMapper {
    public ClientVersionInfoDto query(@Param("clientVersionInfo") ClientVersionInfoDto dto);

    public ClientVersionInfoDto getLastClientVersionInfoDto(int versionCode);
}
