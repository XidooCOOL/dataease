package io.dataease.extensions.push.websocket.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataChangeEvent {
    private String eventId;
    private String eventType;
    private Long timestamp;
    private Long datasourceId;
    private List<Long> affectedTableIds;
    private List<String> affectedDashboardIds;
    private Map<String, Object> extra;
}
