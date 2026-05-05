# Apache Kafka 数据源插件

## 插件介绍

Apache Kafka 数据源插件允许 DataEase 连接 Kafka 消息队列，实时订阅 Topic 数据进行可视化分析。

## 功能特性

- 支持连接 Apache Kafka 集群
- 自动获取 Topic 列表
- 实时消费消息数据
- 支持配置消费者组
- 自动处理消息序列化

## 使用说明

### 步骤一：配置 Kafka 连接

在 DataEase 数据源管理页面，点击"Apache Kafka"图标，填写以下连接信息：

| 参数 | 说明 | 示例 |
| --- | --- | --- |
| Bootstrap Servers | Kafka 集群地址，多个用逗号分隔 | 192.168.1.100:9092,192.168.1.101:9092 |
| Consumer Group | 消费者组 ID（可选） | dataease-consumer |
| Username | 用户名（可选，启用了认证的集群） | admin |
| Password | 密码（可选） | password |

### 步骤二：测试连接

点击"测试连接"按钮，验证配置是否正确。

### 步骤三：选择 Topic

连接成功后，选择要订阅的 Topic，DataEase 将自动读取最新的消息数据。

### 步骤四：创建数据集和可视化

使用订阅的 Topic 数据创建数据集，然后制作可视化图表。

## 技术规格

- 支持 Kafka 版本：2.8.0 及以上
- 数据格式：JSON 字符串
- 字段映射：key、value、partition、offset、timestamp

## 注意事项

1. 确保网络可以访问 Kafka 集群
2. Kafka 集群需要开启 PLAINTEXT 或 SASL 认证
3. 建议使用较新的 Kafka 版本以获得更好的性能
4. 数据消费会从最新 offset 开始读取

## 版本历史

- 1.0.0 - 初始版本，支持基础 Kafka 订阅功能
