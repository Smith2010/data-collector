package com.mazhen.datacollector.service;

import com.mazhen.datacollector.domain.PinganPuhuiUserInfo;
import com.mazhen.datacollector.entity.PinganPuhuiUserEntity;
import com.mazhen.datacollector.repository.PinganPuhuiUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserInfoService {

    @Resource
    private PinganPuhuiUserRepository pinganPuhuiUserRepository;

    public Long addPinganPuhuiUserInfo(PinganPuhuiUserInfo userInfo) {
        PinganPuhuiUserEntity entity = new PinganPuhuiUserEntity();
        BeanUtils.copyProperties(userInfo, entity);
        entity = pinganPuhuiUserRepository.saveAndFlush(entity);
        return entity.getId();
    }

}
