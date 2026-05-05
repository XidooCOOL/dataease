package io.dataease.extensions.push.websocket.handler;

import io.dataease.extensions.push.websocket.service.DataChangeEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

public class DataChangeWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(DataChangeWebSocketHandler.class);
    
    private final DataChangeEventService eventService;
    
    public DataChangeWebSocketHandler(DataChangeEventService eventService) {
        this.eventService = eventService;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String dashboardId = (String) attributes.get("dashboardId");
        
        if (dashboardId != null) {
            eventService.subscribe(dashboardId, session);
            logger.info("WebSocket 连接建立成功，仪表板: {}", dashboardId);
        } else {
            logger.warn("WebSocket 连接缺少 dashboardId 参数");
            session.close(CloseStatus.BAD_DATA);
        }
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        
        if ("ping".equals(payload)) {
            session.sendMessage(new TextMessage("pong"));
            logger.debug("收到心跳请求，回复 pong");
        } else {
            logger.debug("收到消息: {}", payload);
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String dashboardId = (String) attributes.get("dashboardId");
        
        if (dashboardId != null) {
            eventService.unsubscribe(dashboardId, session);
            logger.info("WebSocket 连接关闭，仪表板: {}，关闭状态: {}", dashboardId, status);
        }
        
        eventService.cleanupClosedSessions();
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket 传输错误", exception);
        
        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (Exception e) {
                logger.error("关闭异常连接失败", e);
            }
        }
    }
}
