package io.dataease.extensions.push.websocket.config;

import io.dataease.extensions.push.websocket.handler.DataChangeWebSocketHandler;
import io.dataease.extensions.push.websocket.service.DataChangeEventService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    private final DataChangeEventService eventService;
    
    public WebSocketConfig(DataChangeEventService eventService) {
        this.eventService = eventService;
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DataChangeWebSocketHandler(eventService), "/ws/datachange/{dashboardId}")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
                                                   org.springframework.http.server.ServerHttpResponse response,
                                                   org.springframework.web.socket.WebSocketHandler wsHandler,
                                                   Map<String, Object> attributes) throws Exception {
                        String path = request.getURI().getPath();
                        String[] parts = path.split("/");
                        if (parts.length >= 4) {
                            String dashboardId = parts[3];
                            attributes.put("dashboardId", dashboardId);
                        }
                        return true;
                    }
                })
                .setAllowedOrigins("*");
    }
}
