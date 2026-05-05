package io.dataease.extensions.datasource.kafka.provider;

import io.dataease.extensions.datasource.plugin.DataEaseDatasourcePlugin;
import io.dataease.extensions.datasource.factory.ProviderFactory;
import io.dataease.extensions.datasource.vo.XpackPluginsDatasourceVO;
import io.dataease.plugins.vo.DataEasePluginVO;
import io.dataease.license.utils.JsonUtil;

public class KafkaDataSourcePlugin extends DataEaseDatasourcePlugin {
    
    @Override
    public void loadPlugin() {
        try {
            XpackPluginsDatasourceVO datasourceConfig = getConfig();
            ProviderFactory.loadPlugin(datasourceConfig.getType(), this);
        } catch (Exception e) {
            throw new RuntimeException("加载 Kafka 数据源插件失败", e);
        }
    }
    
    @Override
    public XpackPluginsDatasourceVO getConfig() {
        DataEasePluginVO pluginInfo = null;
        try {
            pluginInfo = getPluginInfo();
            String config = pluginInfo.getConfig();
            XpackPluginsDatasourceVO vo = JsonUtil.parseObject(config, XpackPluginsDatasourceVO.class);
            vo.setIcon(pluginInfo.getIcon());
            return vo;
        } catch (Exception e) {
            throw new RuntimeException("获取插件配置失败", e);
        }
    }
}
