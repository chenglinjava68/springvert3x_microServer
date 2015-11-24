package com.solodream.spring.vertx.service;

import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.mapper.ClientAccountInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientService
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Service
public class ClientService {
    @Autowired
    private ClientAccountInfoMapper clientAccountInfoMapper;

    public boolean login(String account) {
        ClientAccountInfoDto dto = new ClientAccountInfoDto();
        dto.setAccount(account);
        dto = clientAccountInfoMapper.query(dto);
        boolean mark = false;
        if (dto != null) {
            mark = true;
        }
        return mark;
    }
}
