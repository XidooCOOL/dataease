package io.dataease.extensions.embed.dingtalk.dto;

import lombok.Data;

@Data
public class DingtalkUserInfo {
    private String userid;
    private String name;
    private String mobile;
    private String email;
    private String avatar;
    private String unionid;
    private String deptIdList;
}
