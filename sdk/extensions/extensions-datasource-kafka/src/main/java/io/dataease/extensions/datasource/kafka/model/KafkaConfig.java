package io.dataease.extensions.datasource.kafka.model;

import lombok.Data;

@Data
public class KafkaConfig {
    private String bootstrapServers;
    private String groupId;
    private String username;
    private String password;
}
