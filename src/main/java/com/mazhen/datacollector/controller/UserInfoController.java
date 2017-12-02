package com.mazhen.datacollector.controller;

import com.alibaba.fastjson.JSON;
import com.mazhen.datacollector.domain.PinganPuhuiUserInfo;
import com.mazhen.datacollector.service.MessageService;
import com.mazhen.datacollector.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String index() {
        return loan();
    }

    @GetMapping("/loan")
    public String loan() {
        return "loan";
    }

    @PostMapping("/sendCode")
    public String sendCode(@RequestBody String mobile) {
        return messageService.sendMessage(mobile);
    }

    @PostMapping(value = "/submit", consumes = "application/json;charset=UTF-8;", produces = "application/json;charset=UTF-8;")
    public ResponseEntity<String> submit(@RequestBody String userStr) {
        PinganPuhuiUserInfo user = JSON.parseObject(userStr, PinganPuhuiUserInfo.class);
        userInfoService.addPinganPuhuiUserInfo(user);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
