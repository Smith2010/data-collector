package com.mazhen.datacollector.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
@Service
public class MessageService {

    @Value("${data-collector.sms.url}")
    private String smsUrl = "https://dx.ipyy.net/smsJson.aspx?";

    @Value("${data-collector.sms.account}")
    private String account = "AA01287";

    @Value("${data-collector.sms.password}")
    private String password = "AA0128655";

    public String sendMessage(String mobile) {
        Map<String, String> params = Maps.newHashMap();
        params.put("userid", "");
        params.put("account", account);
        params.put("password", DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase());
        params.put("mobile", mobile);
        params.put("content", generateEncodeContent());
        params.put("sendTime", "");
        params.put("action", "send");
        params.put("extno", "");

        return postRequest(smsUrl, params);
    }

    private String generateEncodeContent() {
        try {
            String content = "【金融普惠】您的验证码为" + createRandomCode() + "，2分钟内有效";
            return URLEncoder.encode(new String(content.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Encode issue", e);
        }

        return "";
    }

    private String createRandomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code = code + (int) (Math.random() * 9);
        }
        return code;
    }

    private String postRequest(String url, Map<String, String> params) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
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

    public static void main(String[] args) {
        MessageService service = new MessageService();
        System.out.println(service.sendMessage("13116510947"));
    }
}
