# WebSocket 数据推送插件

## 插件介绍

WebSocket 数据推送插件为 DataEase 提供实时数据变更推送能力，支持仪表板自动刷新，提升数据可视化实时性。

## 功能特性

- 支持 WebSocket 实时推送
- 支持仪表板数据变更自动刷新
- 支持多用户并发连接
- 自动重连机制
- 支持心跳检测

## 使用说明

### 前端集成

在仪表板页面中添加数据刷新组件：

```javascript
import DataRefreshWatcher from '@/components/DataRefreshWatcher.vue'

<template>
  <div>
    <DataRefreshWatcher :dashboardId="dashboardId" @refresh="loadData" />
    <!-- 仪表板内容 -->
  </div>
</template>
```

### 后端配置

插件会自动配置 WebSocket 端点：

```
ws://your-domain.com/ws/datachange/{dashboardId}
```

### 刷新模式

1. **WebSocket 推送模式**：数据变更时主动推送（推荐）
2. **轮询模式**：定时拉取数据更新

## 技术规格

- WebSocket 协议：RFC 6455
- 消息格式：JSON
- 心跳间隔：30 秒
- 自动重连间隔：5 秒

## 注意事项

1. 确保前端网络支持 WebSocket 连接
2. 建议配合反向代理（如 Nginx）使用
3. 生产环境建议启用 SSL（WSS）

## 版本历史

- 1.0.0 - 初始版本，支持 WebSocket 推送和轮询两种模式
