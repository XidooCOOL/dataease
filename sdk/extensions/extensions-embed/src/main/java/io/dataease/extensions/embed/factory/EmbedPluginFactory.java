package io.dataease.extensions.embed.factory;

import io.dataease.extensions.embed.plugin.DataEaseEmbedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmbedPluginFactory {
    private static final Logger logger = LoggerFactory.getLogger(EmbedPluginFactory.class);
    private static final Map<String, DataEaseEmbedPlugin> pluginMap = new ConcurrentHashMap<>();

    public static void loadPlugin(String type, DataEaseEmbedPlugin plugin) {
        if (pluginMap.containsKey(type)) {
            logger.warn("嵌入插件类型 {} 已存在，跳过加载", type);
            return;
        }
        pluginMap.put(type, plugin);
        logger.info("嵌入插件类型 {} 加载成功", type);
    }

    public static DataEaseEmbedPlugin getPlugin(String type) {
        return pluginMap.get(type);
    }

    public static Map<String, DataEaseEmbedPlugin> getAllPlugins() {
        return pluginMap;
    }
}
