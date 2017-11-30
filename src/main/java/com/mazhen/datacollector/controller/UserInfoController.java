package com.mazhen.datacollector.controller;

import com.mazhen.datacollector.domain.PinganPuhuiUserInfo;
import com.mazhen.datacollector.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller("/pingan/puhui")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/loan")
    public String loan() {
        return "loan";
    }

    @PostMapping("/submit")
    public String submit(@RequestBody PinganPuhuiUserInfo user) {
        userInfoService.addPinganPuhuiUserInfo(user);
        return "success";
    }

}
