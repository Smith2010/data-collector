package com.mazhen.datacollector.service;

import com.mazhen.datacollector.entity.PinganPuhuiUserInfo;
import com.mazhen.datacollector.repository.PinganPuhuiUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserInfoService {

    @Resource
    private PinganPuhuiUserRepository pinganPuhuiUserRepository;

    public void addPinganPuhuiUserInfo(PinganPuhuiUserInfo userInfo) {
        pinganPuhuiUserRepository.saveAndFlush(userInfo);
    }

}
