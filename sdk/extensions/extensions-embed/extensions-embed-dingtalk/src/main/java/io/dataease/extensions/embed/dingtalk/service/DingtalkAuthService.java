package io.dataease.extensions.embed.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.dataease.extensions.embed.dingtalk.dto.DingtalkUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class DingtalkAuthService {
    private static final Logger logger = LoggerFactory.getLogger(DingtalkAuthService.class);
    
    private final RestTemplate restTemplate;
    private String appKey;
    private String appSecret;
    
    public DingtalkAuthService(String appKey, String appSecret) {
        this.restTemplate = new RestTemplate();
        this.appKey = appKey;
        this.appSecret = appSecret;
    }
    
    public String getAccessToken() {
        String url = "https://oapi.dingtalk.com/gettoken?appkey={appKey}&appsecret={appSecret}";
        
        Map<String, String> params = new HashMap<>();
        params.put("appKey", appKey);
        params.put("appSecret", appSecret);
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
            JSONObject result = JSON.parseObject(response.getBody());
            
            if ("0".equals(result.getString("errcode"))) {
                return result.getString("access_token");
            } else {
                logger.error("获取钉钉 AccessToken 失败: {}", result.getString("errmsg"));
                throw new RuntimeException("获取钉钉 AccessToken 失败: " + result.getString("errmsg"));
            }
        } catch (Exception e) {
            logger.error("获取钉钉 AccessToken 异常", e);
            throw new RuntimeException("获取钉钉 AccessToken 异常", e);
        }
    }
    
    public String getUseridByAuthCode(String authCode, String accessToken) {
        String url = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("auth_code", authCode);
        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                String.class,
                Map.of("access_token", accessToken)
            );
            
            JSONObject body = JSON.parseObject(response.getBody());
            
            if ("0".equals(body.getString("errcode"))) {
                JSONObject result = body.getJSONObject("result");
                return result.getString("userid");
            } else {
                logger.error("获取用户 UserId 失败: {}", body.getString("errmsg"));
                throw new RuntimeException("获取用户 UserId 失败: " + body.getString("errmsg"));
            }
        } catch (Exception e) {
            logger.error("获取用户 UserId 异常", e);
            throw new RuntimeException("获取用户 UserId 异常", e);
        }
    }
    
    public DingtalkUserInfo getUserDetail(String accessToken, String userid) {
        String url = "https://oapi.dingtalk.com/topapi/v2/user/get";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userid", userid);
        requestBody.put("language", "zh_CN");
        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class,
                Map.of("access_token", accessToken)
            );
            
            JSONObject body = JSON.parseObject(response.getBody());
            
            if ("0".equals(body.getString("errcode"))) {
                JSONObject result = body.getJSONObject("result");
                
                DingtalkUserInfo userInfo = new DingtalkUserInfo();
                userInfo.setUserid(result.getString("userid"));
                userInfo.setName(result.getString("name"));
                userInfo.setMobile(result.getString("mobile"));
                userInfo.setEmail(result.getString("email"));
                userInfo.setAvatar(result.getString("avatar"));
                userInfo.setUnionid(result.getString("unionid"));
                
                if (result.containsKey("dept_id_list")) {
                    userInfo.setDeptIdList(result.getJSONArray("dept_id_list").toJSONString());
                }
                
                return userInfo;
            } else {
                logger.error("获取用户详情失败: {}", body.getString("errmsg"));
                throw new RuntimeException("获取用户详情失败: " + body.getString("errmsg"));
            }
        } catch (Exception e) {
            logger.error("获取用户详情异常", e);
            throw new RuntimeException("获取用户详情异常", e);
        }
    }
    
    public DingtalkUserInfo getUserInfoByAuthCode(String authCode) {
        String accessToken = getAccessToken();
        String userid = getUseridByAuthCode(authCode, accessToken);
        return getUserDetail(accessToken, userid);
    }
}
