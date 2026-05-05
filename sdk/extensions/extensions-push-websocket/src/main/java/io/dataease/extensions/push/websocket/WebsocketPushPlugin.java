package io.dataease.extensions.push.websocket;

import io.dataease.extensions.embed.plugin.DataEaseEmbedPlugin;
import io.dataease.extensions.embed.factory.EmbedPluginFactory;
import io.dataease.plugins.vo.DataEasePluginVO;

import java.util.HashMap;
import java.util.Map;

public class WebsocketPushPlugin extends DataEaseEmbedPlugin {
    
    @Override
    public void loadPlugin() {
        try {
            DataEasePluginVO pluginInfo = getPluginInfo();
            EmbedPluginFactory.loadPlugin("websocket-push", this);
        } catch (Exception e) {
            throw new RuntimeException("加载 WebSocket 推送插件失败", e);
        }
    }
    
    @Override
    public DataEasePluginVO getPluginInfo() throws Exception {
        DataEasePluginVO pluginInfo = new DataEasePluginVO();
        pluginInfo.setPluginId("push-websocket");
        pluginInfo.setPluginVersion("1.0.0");
        Map<String, Object> config = new HashMap<>();
        config.put("type", "websocket-push");
        config.put("name", "WebSocket 数据推送");
        config.put("description", "提供实时数据变更推送能力");
        pluginInfo.setConfig(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(config));
        return pluginInfo;
    }
    
    @Override
    public String getEmbedType() {
        return "websocket-push";
    }
    
    @Override
    public Map<String, Object> getEmbedConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("type", "websocket-push");
        config.put("name", "WebSocket 数据推送");
        config.put("heartbeat_interval", 30000);
        config.put("reconnect_interval", 5000);
        return config;
    }
}
