package io.dataease.extensions.datasource.lark.config;

import lombok.Data;

@Data
public class LarkConfig {
    private String appToken;
    private String appId;
    private String appSecret;
    private String userAccessToken;
    private String tenantAccessToken;
}
