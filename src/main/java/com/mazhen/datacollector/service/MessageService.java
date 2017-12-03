package com.mazhen.datacollector.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class MessageService {

    @Value("${data-collector.sms.url}")
    private String smsUrl;

    @Value("${data-collector.sms.account}")
    private String account;

    @Value("${data-collector.sms.password}")
    private String password;

    public String sendMessage(String mobile) {
        String code = createRandomCode();

        Map<String, String> params = Maps.newHashMap();
        params.put("userid", "");
        params.put("account", account);
        params.put("password", DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase());
        params.put("mobile", mobile);
        params.put("content", generateEncodeContent(code));
        params.put("sendTime", "");
        params.put("action", "send");
        params.put("extno", "");

        String result = postRequest(smsUrl, params);
        Map<String,String> resultMap = (Map<String,String>) JSON.parse(result);

        if ("Success".equals(resultMap.get("returnstatus"))) {
            return code;
        } else {
            return "0";
        }
    }

    private String generateEncodeContent(String code) {
        return "【金融普惠】您的验证码为" + code + "，2分钟内有效";
    }

    private String createRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 9));
        }
        return code.toString();
    }

    private String postRequest(String url, Map<String, String> params) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        postMethod.setRequestHeader("Connection", "close");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            postMethod.addParameter(entry.getKey(), entry.getValue());
        }

        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

        String result = null;
        try {
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error("Invalid URL" + url, e);
        } catch (IOException e) {
            log.error("Network failed", e);
        } finally {
            postMethod.releaseConnection();
            if (httpClient != null) {
                ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
                httpClient = null;
            }
        }
        return result;
    }
}
