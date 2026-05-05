package io.dataease.extensions.embed.plugin;

import io.dataease.plugins.vo.DataEasePluginVO;

import java.util.Map;

public abstract class DataEaseEmbedPlugin {
    
    public abstract void loadPlugin();
    
    public abstract DataEasePluginVO getPluginInfo() throws Exception;
    
    public abstract String getEmbedType();
    
    public abstract Map<String, Object> getEmbedConfig();
}
