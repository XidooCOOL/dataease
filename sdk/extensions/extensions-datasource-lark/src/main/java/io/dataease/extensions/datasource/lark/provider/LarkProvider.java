package io.dataease.extensions.datasource.lark.provider;

import io.dataease.extensions.datasource.provider.Provider;
import io.dataease.extensions.datasource.dto.*;
import io.dataease.extensions.datasource.lark.config.LarkConfig;
import io.dataease.extensions.datasource.lark.service.LarkService;
import io.dataease.exception.DEException;
import com.lark.oapi.core.response.DataList;
import com.lark.oapi.service.bitable.v1.model.*;

import java.util.*;

public class LarkProvider extends Provider {

    private LarkService larkService;

    @Override
    public List<String> getSchema(DatasourceRequest datasourceRequest) {
        try {
            LarkConfig config = parseConfig(datasourceRequest.getDatasource());
            initService(config);
            
            List<AppTable> tables = larkService.listTables();
            List<String> tableNames = new ArrayList<>();
            for (AppTable table : tables) {
                tableNames.add(table.getName());
            }
            return tableNames;
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public List<DatasetTableDTO> getTables(DatasourceRequest datasourceRequest) {
        try {
            LarkConfig config = parseConfig(datasourceRequest.getDatasource());
            initService(config);
            
            List<AppTable> tables = larkService.listTables();
            List<DatasetTableDTO> result = new ArrayList<>();
            
            for (AppTable table : tables) {
                DatasetTableDTO dto = new DatasetTableDTO();
                dto.setName(table.getName());
                dto.setTableType("bitable");
                result.add(dto);
            }
            
            return result;
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public ConnectionObj getConnection(DatasourceDTO coreDatasource) throws Exception {
        ConnectionObj obj = new ConnectionObj();
        try {
            LarkConfig config = parseConfig(coreDatasource);
            initService(config);
            boolean success = larkService.testConnection();
            obj.setStatus(success);
        } catch (Exception e) {
            obj.setStatus(false);
            throw e;
        }
        return obj;
    }

    @Override
    public String checkStatus(DatasourceRequest datasourceRequest) throws Exception {
        try {
            LarkConfig config = parseConfig(datasourceRequest.getDatasource());
            initService(config);
            boolean success = larkService.testConnection();
            return success ? "success" : "failed";
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public Map<String, Object> fetchResultField(DatasourceRequest datasourceRequest) throws DEException {
        Map<String, Object> result = new HashMap<>();
        try {
            LarkConfig config = parseConfig(datasourceRequest.getDatasource());
            initService(config);
            
            String tableName = datasourceRequest.getTable();
            String tableId = datasourceRequest.getDatasource().getExtraData().getOrDefault("tableId_" + tableName, "").toString();
            
            Integer pageSize = datasourceRequest.getLimit() != null ? datasourceRequest.getLimit().intValue() : 100;
            List<Map<String, Object>> dataList = larkService.getRecords(tableId, pageSize);
            
            result.put("data", dataList);
            return result;
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public List<TableField> fetchTableField(DatasourceRequest datasourceRequest) throws DEException {
        try {
            LarkConfig config = parseConfig(datasourceRequest.getDatasource());
            initService(config);
            
            String tableName = datasourceRequest.getTable();
            String tableId = datasourceRequest.getDatasource().getExtraData().getOrDefault("tableId_" + tableName, "").toString();
            
            List<TableField> fields = larkService.getFields(tableId);
            return fields;
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public void hidePW(DatasourceDTO datasourceDTO) {
    }

    private void initService(LarkConfig config) {
        if (larkService == null) {
            larkService = new LarkService(config);
        }
    }

    private LarkConfig parseConfig(DatasourceDTO datasourceDTO) {
        Map<String, Object> configMap = (Map<String, Object>) datasourceDTO.getConfiguration();
        LarkConfig config = new LarkConfig();
        config.setAppToken((String) configMap.get("appToken"));
        return config;
    }
}
