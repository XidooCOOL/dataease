package io.dataease.extensions.embed.dingtalk;

import io.dataease.extensions.embed.plugin.DataEaseEmbedPlugin;
import io.dataease.extensions.embed.factory.EmbedPluginFactory;
import io.dataease.plugins.vo.DataEasePluginVO;

import java.util.HashMap;
import java.util.Map;

public class DingtalkEmbedPlugin extends DataEaseEmbedPlugin {
    
    @Override
    public void loadPlugin() {
        try {
            DataEasePluginVO pluginInfo = getPluginInfo();
            EmbedPluginFactory.loadPlugin("dingtalk", this);
        } catch (Exception e) {
            throw new RuntimeException("加载钉钉嵌入插件失败", e);
        }
    }
    
    @Override
    public DataEasePluginVO getPluginInfo() throws Exception {
        DataEasePluginVO pluginInfo = new DataEasePluginVO();
        pluginInfo.setPluginId("embed-dingtalk");
        pluginInfo.setPluginVersion("1.0.0");
        Map<String, Object> config = new HashMap<>();
        config.put("type", "dingtalk");
        config.put("name", "钉钉嵌入");
        config.put("description", "支持钉钉 SSO 和嵌入集成");
        pluginInfo.setConfig(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(config));
        return pluginInfo;
    }
    
    @Override
    public String getEmbedType() {
        return "dingtalk";
    }
    
    @Override
    public Map<String, Object> getEmbedConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("type", "dingtalk");
        config.put("name", "钉钉嵌入");
        config.put("auth_url", "https://oapi.dingtalk.com/gettoken");
        config.put("userinfo_url", "https://oapi.dingtalk.com/topapi/v2/user/get");
        return config;
    }
}
