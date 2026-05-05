package io.dataease.extensions.datasource.lark.service;

import io.dataease.extensions.datasource.dto.TableField;
import io.dataease.extensions.datasource.lark.config.LarkConfig;
import com.lark.oapi.core.Config;
import com.lark.oapi.core.enums.AppType;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.service.bitable.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LarkService {
    private static final Logger logger = LoggerFactory.getLogger(LarkService.class);
    
    private final LarkConfig config;
    private final Config larkConfig;

    public LarkService(LarkConfig config) {
        this.config = config;
        
        if (config.getAppId() != null && config.getAppSecret() != null) {
            this.larkConfig = Config.newConfig(
                config.getAppId(),
                config.getAppSecret(),
                ""
            ).appType(AppType.SelfBuilt);
        } else if (config.getUserAccessToken() != null) {
            this.larkConfig = Config.newConfig(
                config.getUserAccessToken()
            ).appType(AppType.SelfBuilt);
        } else {
            this.larkConfig = Config.newConfig(
                config.getAppToken(),
                ""
            ).appType(AppType.SelfBuilt);
        }
    }

    public boolean testConnection() {
        try {
            listTables();
            return true;
        } catch (Exception e) {
            logger.error("飞书连接测试失败", e);
            return false;
        }
    }

    public List<AppTable> listTables() throws Exception {
        ListAppTablesRequest req = ListAppTablesRequest.newBuilder()
            .build();
        
        RequestOptions options = RequestOptions.newBuilder()
            .pathParameters(Map.of("app_token", config.getAppToken()))
            .build();
        
        ListAppTablesResponse resp = com.lark.oapi.service.bitable.v1.AppTableClient.Factory.create(larkConfig)
            .listAppTables(req, options);
        
        if (!resp.success()) {
            throw new RuntimeException("获取数据表列表失败: " + resp.getMsg());
        }
        
        return resp.getData().getItems() != null ? resp.getData().getItems() : new ArrayList<>();
    }

    public List<TableField> getFields(String tableId) throws Exception {
        ListFieldReq req = ListFieldReq.newBuilder()
            .build();
        
        RequestOptions options = RequestOptions.newBuilder()
            .pathParameters(Map.of(
                "app_token", config.getAppToken(),
                "table_id", tableId
            ))
            .build();
        
        ListFieldResp resp = com.lark.oapi.service.bitable.v1.AppTableFieldClient.Factory.create(larkConfig)
            .listField(req, options);
        
        if (!resp.success()) {
            throw new RuntimeException("获取字段列表失败: " + resp.getMsg());
        }
        
        List<TableField> fields = new ArrayList<>();
        List<Field> larkFields = resp.getData().getItems();
        
        if (larkFields != null) {
            for (Field field : larkFields) {
                TableField tableField = new TableField();
                tableField.setFieldName(field.getFieldName());
                tableField.setFieldType(convertFieldType(field.getType()));
                tableField.setPrimaryKey(false);
                fields.add(tableField);
            }
        }
        
        return fields;
    }

    public List<Map<String, Object>> getRecords(String tableId, int pageSize) throws Exception {
        ListRecordReq req = ListRecordReq.newBuilder()
            .pageSize(pageSize)
            .build();
        
        RequestOptions options = RequestOptions.newBuilder()
            .pathParameters(Map.of(
                "app_token", config.getAppToken(),
                "table_id", tableId
            ))
            .build();
        
        ListRecordResp resp = com.lark.oapi.service.bitable.v1.AppTableRecordClient.Factory.create(larkConfig)
            .listRecord(req, options);
        
        if (!resp.success()) {
            throw new RuntimeException("获取记录列表失败: " + resp.getMsg());
        }
        
        List<Map<String, Object>> records = new ArrayList<>();
        List<Record> larkRecords = resp.getData().getItems();
        
        if (larkRecords != null) {
            for (Record record : larkRecords) {
                Map<String, Object> recordMap = new HashMap<>();
                Map<String, Object> fields = record.getFields();
                if (fields != null) {
                    recordMap.putAll(fields);
                }
                recordMap.put("record_id", record.getRecordId());
                records.add(recordMap);
            }
        }
        
        return records;
    }

    private String convertFieldType(int type) {
        switch (type) {
            case 1:
                return "number";
            case 2:
                return "string";
            case 3:
                return "datetime";
            case 4:
                return "checkbox";
            case 5:
                return "string";
            case 7:
                return "number";
            case 11:
                return "number";
            case 13:
                return "datetime";
            case 15:
                return "string";
            case 18:
                return "string";
            case 19:
                return "string";
            case 21:
                return "number";
            default:
                return "string";
        }
    }
}
