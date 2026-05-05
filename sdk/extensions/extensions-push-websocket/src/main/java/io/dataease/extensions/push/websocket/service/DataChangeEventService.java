package io.dataease.extensions.push.websocket.service;

import io.dataease.extensions.push.websocket.dto.DataChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataChangeEventService {
    private static final Logger logger = LoggerFactory.getLogger(DataChangeEventService.class);
    
    private final Map<String, List<WebSocketSession>> dashboardSubscribers = new ConcurrentHashMap<>();
    
    public void subscribe(String dashboardId, WebSocketSession session) {
        dashboardSubscribers.computeIfAbsent(dashboardId, k -> new java.util.ArrayList<>()).add(session);
        logger.info("仪表板 {} 订阅成功，当前订阅数: {}", dashboardId, getSubscriberCount(dashboardId));
    }
    
    public void unsubscribe(String dashboardId, WebSocketSession session) {
        List<WebSocketSession> sessions = dashboardSubscribers.get(dashboardId);
        if (sessions != null) {
            sessions.remove(session);
            logger.info("仪表板 {} 取消订阅，当前订阅数: {}", dashboardId, getSubscriberCount(dashboardId));
        }
    }
    
    public void publishEvent(DataChangeEvent event) {
        if (event.getAffectedDashboardIds() == null || event.getAffectedDashboardIds().isEmpty()) {
            logger.debug("事件没有影响任何仪表板，跳过推送");
            return;
        }
        
        for (String dashboardId : event.getAffectedDashboardIds()) {
            List<WebSocketSession> sessions = dashboardSubscribers.get(dashboardId);
            if (sessions != null && !sessions.isEmpty()) {
                logger.info("向仪表板 {} 推送事件 {}，订阅数: {}", dashboardId, event.getEventId(), sessions.size());
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        try {
                            String message = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(event);
                            session.sendMessage(new org.springframework.web.socket.TextMessage(message));
                        } catch (Exception e) {
                            logger.error("推送事件到仪表板 {} 失败", dashboardId, e);
                        }
                    }
                }
            }
        }
    }
    
    public void cleanupClosedSessions() {
        for (Map.Entry<String, List<WebSocketSession>> entry : dashboardSubscribers.entrySet()) {
            entry.getValue().removeIf(session -> !session.isOpen());
        }
    }
    
    public int getSubscriberCount(String dashboardId) {
        List<WebSocketSession> sessions = dashboardSubscribers.get(dashboardId);
        return sessions != null ? sessions.size() : 0;
    }
    
    public int getTotalSubscriberCount() {
        return dashboardSubscribers.values().stream()
            .mapToInt(List::size)
            .sum();
    }
}
