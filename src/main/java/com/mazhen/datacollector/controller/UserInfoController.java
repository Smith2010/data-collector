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

import javax.servlet.http.HttpSession;

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

    @PostMapping(value = "/sendCode", consumes = "text/plain;charset=UTF-8;")
    public ResponseEntity<String> sendCode(@RequestBody String mobile, HttpSession session) {
        String code = messageService.sendMessage(mobile);

        String result;
        if ("0".equals(code)) {
            result = "failed";
        } else {
            session.setAttribute(mobile, code);
            result = "success";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/submit", consumes = "application/json;charset=UTF-8;")
    public ResponseEntity<String> submit(@RequestBody String userStr, HttpSession session) {
        String result;

        PinganPuhuiUserInfo user = JSON.parseObject(userStr, PinganPuhuiUserInfo.class);
        String code = (String) session.getAttribute(user.getMobile());
        if (code.equals(user.getCode())) {
            userInfoService.addPinganPuhuiUserInfo(user);
            result = "success";
        } else {
            result = "failed";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
