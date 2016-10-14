package com.solodream.spring.vertx.service;

import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.jpa.domain.ClientVersionInfoDto;
import com.solodream.spring.vertx.mapper.UserMapper;
import com.solodream.spring.vertx.mapper.ClientVersionInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientService
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClientVersionInfoMapper clientVersionInfoMapper;

    public ClientAccountInfoDto login(String account) {
        ClientAccountInfoDto dto = new ClientAccountInfoDto();
        dto.setAccount(account);
        ClientAccountInfoDto result = userMapper.query(dto);
        return result;
    }

    public ClientVersionInfoDto getLastClientVersionInfoDto(int versionCode) {
        ClientVersionInfoDto versionInfoDto = clientVersionInfoMapper.getLastClientVersionInfoDto(versionCode);
        return versionInfoDto;
    }
}
