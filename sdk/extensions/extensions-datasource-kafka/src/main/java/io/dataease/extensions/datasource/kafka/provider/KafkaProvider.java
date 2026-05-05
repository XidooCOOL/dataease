package io.dataease.extensions.datasource.kafka.provider;

import io.dataease.extensions.datasource.provider.Provider;
import io.dataease.extensions.datasource.dto.*;
import io.dataease.extensions.datasource.kafka.model.KafkaConfig;
import io.dataease.exception.DEException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class KafkaProvider extends Provider {

    @Override
    public List<String> getSchema(DatasourceRequest datasourceRequest) {
        List<String> topics = new ArrayList<>();
        try {
            KafkaConfig config = parseConfig(datasourceRequest.getDatasource());
            try (Consumer<String, String> consumer = createConsumer(config)) {
                topics.addAll(consumer.listTopics(Duration.ofSeconds(5)).keySet());
            }
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
        return topics;
    }

    @Override
    public List<DatasetTableDTO> getTables(DatasourceRequest datasourceRequest) {
        List<String> topics = getSchema(datasourceRequest);
        List<DatasetTableDTO> tables = new ArrayList<>();
        for (String topic : topics) {
            DatasetTableDTO table = new DatasetTableDTO();
            table.setName(topic);
            table.setTableType("topic");
            tables.add(table);
        }
        return tables;
    }

    @Override
    public ConnectionObj getConnection(DatasourceDTO coreDatasource) throws Exception {
        ConnectionObj obj = new ConnectionObj();
        obj.setStatus(true);
        return obj;
    }

    @Override
    public String checkStatus(DatasourceRequest datasourceRequest) throws Exception {
        try {
            getSchema(datasourceRequest);
            return "success";
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public Map<String, Object> fetchResultField(DatasourceRequest datasourceRequest) throws DEException {
        Map<String, Object> result = new HashMap<>();
        try {
            KafkaConfig config = parseConfig(datasourceRequest.getDatasource());
            String topic = datasourceRequest.getTable();
            Integer limit = datasourceRequest.getLimit() != null ? datasourceRequest.getLimit() : 100;
            Duration timeout = Duration.ofSeconds(10);

            List<Map<String, Object>> dataList = new ArrayList<>();

            try (Consumer<String, String> consumer = createConsumer(config)) {
                consumer.subscribe(Collections.singletonList(topic));
                int count = 0;
                long startTime = System.currentTimeMillis();
                while (count < limit && (System.currentTimeMillis() - startTime) < timeout.toMillis()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        Map<String, Object> recordMap = new HashMap<>();
                        recordMap.put("key", record.key());
                        recordMap.put("value", record.value());
                        recordMap.put("partition", record.partition());
                        recordMap.put("offset", record.offset());
                        recordMap.put("timestamp", record.timestamp());
                        dataList.add(recordMap);
                        count++;
                        if (count >= limit) break;
                    }
                }
            }

            result.put("data", dataList);
            return result;
        } catch (Exception e) {
            throw DEException.checkedError(e);
        }
    }

    @Override
    public List<TableField> fetchTableField(DatasourceRequest datasourceRequest) throws DEException {
        List<TableField> fields = new ArrayList<>();
        
        TableField keyField = new TableField();
        keyField.setFieldName("key");
        keyField.setFieldType("string");
        keyField.setPrimaryKey(false);
        fields.add(keyField);
        
        TableField valueField = new TableField();
        valueField.setFieldName("value");
        valueField.setFieldType("string");
        valueField.setPrimaryKey(false);
        fields.add(valueField);
        
        TableField partitionField = new TableField();
        partitionField.setFieldName("partition");
        partitionField.setFieldType("number");
        partitionField.setPrimaryKey(false);
        fields.add(partitionField);
        
        TableField offsetField = new TableField();
        offsetField.setFieldName("offset");
        offsetField.setFieldType("number");
        offsetField.setPrimaryKey(false);
        fields.add(offsetField);
        
        TableField timestampField = new TableField();
        timestampField.setFieldName("timestamp");
        timestampField.setFieldType("datetime");
        timestampField.setPrimaryKey(false);
        fields.add(timestampField);
        
        return fields;
    }

    @Override
    public void hidePW(DatasourceDTO datasourceDTO) {
    }

    private Consumer<String, String> createConsumer(KafkaConfig config) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId() != null ? config.getGroupId() : "dataease-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        
        if (config.getUsername() != null && !config.getUsername().isEmpty()) {
            props.put("security.protocol", "SASL_PLAINTEXT");
            props.put("sasl.mechanism", "PLAIN");
            props.put("sasl.jaas.config", 
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + 
                config.getUsername() + "\" password=\"" + config.getPassword() + "\";");
        }
        
        return new KafkaConsumer<>(props);
    }

    private KafkaConfig parseConfig(DatasourceDTO datasourceDTO) {
        Map<String, Object> configMap = (Map<String, Object>) datasourceDTO.getConfiguration();
        KafkaConfig config = new KafkaConfig();
        config.setBootstrapServers((String) configMap.get("bootstrapServers"));
        config.setGroupId((String) configMap.get("groupId"));
        config.setUsername((String) configMap.get("username"));
        config.setPassword((String) configMap.get("password"));
        return config;
    }
}
