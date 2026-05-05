# DataEase 插件安装与使用指南

本指南详细说明了 DataEase 插件的安装、配置和开发流程。

## 目录

- [插件概述](#插件概述)
- [插件安装](#插件安装)
- [插件使用](#插件使用)
- [插件开发](#插件开发)
- [常见问题](#常见问题)

---

## 插件概述

DataEase 插件系统允许您扩展 DataEase 的功能，包括：

| 插件类型 | 说明 | 基类 |
|---------|-----|------|
| **数据源插件** | 支持连接更多数据源类型 | `DataEaseDatasourcePlugin` |
| **图表插件** | 添加自定义图表类型 | `DataEaseChartPlugin` |
| **嵌入插件** | 与第三方平台集成（SSO、嵌入） | `DataEaseEmbedPlugin` |
| **数据填报插件** | 扩展数据填报功能 | `DataFillingPlugin` |
| **推送插件** | 支持实时数据推送 | 自定义接口 |

### 官方插件列表

| 插件名称 | 类型 | 描述 | 版本要求 |
|---------|-----|------|---------|
| 飞书数据源插件 | 数据源 | 连接飞书多维表格 | v2.10.21+ |
| Kafka 数据源插件 | 数据源 | 连接 Kafka 消息队列 | v2.10.0+ |
| 钉钉嵌入插件 | 嵌入 | 钉钉 SSO 和工作台集成 | v2.8.0+ |
| WebSocket 推送插件 | 推送 | 实时数据变更推送 | v2.11.0+ |

---

## 插件安装

### 前提条件

- 已安装 DataEase v2.0 或更高版本
- 管理员权限（用于安装插件）
- 已获取插件 jar 包

### 方式一：通过 Web UI 安装（推荐）

这是最简单和最常用的安装方式：

#### 1. 访问插件管理页面

1. 登录 DataEase 系统
2. 点击顶部导航栏【系统设置】
3. 选择【插件管理】选项卡

#### 2. 上传插件

1. 点击【上传插件】按钮
2. 在弹出的文件选择框中选择插件 jar 包
3. 等待上传完成

#### 3. 安装插件

1. 上传成功后，系统会显示插件信息
2. 确认插件信息无误后，点击【安装】按钮
3. 等待安装过程完成（通常几秒钟）
4. 安装成功后会显示安装完成提示

#### 4. 验证安装

刷新页面或进入相关功能模块，验证插件是否正常工作。

### 方式二：手动部署

适用于企业版或需要离线部署的场景：

#### 1. 准备插件文件

将插件 jar 包准备好，例如：`my-datasource-plugin-1.0.0.jar`

#### 2. 上传到服务器

使用 scp 或其他工具将插件上传到 DataEase 服务器：

```bash
scp my-datasource-plugin-1.0.0.jar root@your-server:/tmp/
```

#### 3. 复制到插件目录

```bash
# 进入 DataEase 安装目录
cd /opt/dataease2.0/

# 确保插件目录存在
mkdir -p plugins/

# 复制插件文件
cp /tmp/my-datasource-plugin-1.0.0.jar plugins/
```

#### 4. 重启服务

```bash
# 停止 DataEase
./bin/stop.sh

# 启动 DataEase
./bin/start.sh

# 或者直接重启
./bin/restart.sh
```

#### 5. 验证安装

登录系统检查插件是否已加载。

### 方式三：通过 API 安装

适用于自动化部署场景：

```bash
# 1. 获取认证 Token
# 参考 api_readme.md 中的登录接口

# 2. 上传插件
curl -X POST "http://your-dataease/api/plugin/install" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "file=@/path/to/your-plugin.jar"
```

---

## 插件使用

### 飞书数据源插件

#### 1. 配置飞书应用对接

1. 进入【系统设置】>【平台对接】
2. 找到飞书应用配置
3. 填写 AppID 和 AppSecret
4. 启用飞书应用对接

#### 2. 获取飞书多维表格权限

确保目标飞书多维表格已授权给飞书应用访问。

#### 3. 创建飞书数据源

1. 进入【数据源管理】
2. 点击【新建数据源】
3. 选择【飞书多维表格】类型
4. 填写多维表格的 app_token
5. 点击【获取数据表】测试连接
6. 保存数据源

#### 4. 使用飞书数据

在数据集或图表中直接使用飞书数据源，与其他数据源使用方式一致。

### 钉钉嵌入插件

#### 1. 配置钉钉应用

1. 登录 [钉钉开放平台](https://open.dingtalk.com/)
2. 创建企业内部应用
3. 获取 AppKey 和 AppSecret

#### 2. 在 DataEase 配置插件

1. 进入【系统设置】>【插件管理】
2. 找到钉钉嵌入插件
3. 点击【配置】
4. 填写 AppKey 和 AppSecret
5. 启用 SSO 选项
6. 保存配置

#### 3. 生成嵌入链接

1. 打开要嵌入的仪表板
2. 点击【分享】>【嵌入链接】
3. 选择【钉钉嵌入】
4. 复制生成的链接
5. 在钉钉工作台中配置该链接

### WebSocket 推送插件

1. 安装插件后会自动启用
2. 在仪表板编辑页面开启【实时刷新】选项
3. 数据变更时会自动推送更新

---

## 插件开发

### 开发环境要求

- JDK 8+
- Maven 3.6+
- DataEase 源码或 SDK

### 快速开始

#### 1. 创建插件项目

```bash
# 参考现有插件结构
cp -r sdk/extensions/extensions-datasource-lark my-plugin
cd my-plugin
```

#### 2. 配置 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>io.dataease</groupId>
        <artifactId>dataease-extensions</artifactId>
        <version>2.0.0</version>
    </parent>
    
    <artifactId>my-datasource-plugin</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <dependencies>
        <!-- 依赖 DataEase 扩展 API -->
        <dependency>
            <groupId>io.dataease</groupId>
            <artifactId>extensions-datasource</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</project>
```

#### 3. 创建 data.yaml 配置文件

```yaml
name: 我的数据源插件
title: 自定义数据源
description: 支持连接自定义数据源
tags:
  - 数据源插件
  - 自定义
additionalProperties:
  enterprise: false
```

#### 4. 实现插件类

```java
package io.dataease.extensions.mydatasource;

import io.dataease.extensions.datasource.plugin.DataEaseDatasourcePlugin;
import io.dataease.plugins.template.DataEasePlugin;

@DataEasePlugin(name = "my-datasource-plugin")
public class MyDatasourcePlugin extends DataEaseDatasourcePlugin {
    
    @Override
    public void loadPlugin() {
        // 插件加载时执行
        System.out.println("MyDatasourcePlugin loaded!");
        
        // 初始化插件
        initPlugin();
    }
    
    @Override
    public void unloadPlugin() {
        // 插件卸载时执行
        System.out.println("MyDatasourcePlugin unloaded!");
        
        // 清理资源
        cleanup();
    }
    
    private void initPlugin() {
        // 你的初始化逻辑
    }
    
    private void cleanup() {
        // 你的清理逻辑
    }
}
```

#### 5. 实现 Provider 接口（数据源插件）

```java
package io.dataease.extensions.mydatasource.provider;

import io.dataease.extensions.datasource.provider.Provider;
import io.dataease.extensions.datasource.dto.DatasourceRequest;

import java.util.List;
import java.util.Map;

public class MyDatasourceProvider implements Provider {
    
    @Override
    public List<String> getSchema(DatasourceRequest request) {
        // 获取数据库 schema 列表
        return List.of("schema1", "schema2");
    }
    
    @Override
    public Map<String, Object> fetchData(DatasourceRequest request) {
        // 获取数据
        Map<String, Object> result = Map.of(
            "columns", List.of("id", "name"),
            "data", List.of(List.of(1, "test"))
        );
        return result;
    }
}
```

#### 6. 打包插件

```bash
mvn clean package
```

打包成功后，会在 `target/` 目录生成插件 jar 包。

### 插件测试

1. 使用上述方法安装插件到测试环境
2. 验证插件功能是否正常
3. 检查日志是否有错误

---

## 插件管理 API

### 查询已安装插件

```http
GET /api/plugin/query
Authorization: Bearer YOUR_TOKEN
```

### 安装插件

```http
POST /api/plugin/install
Authorization: Bearer YOUR_TOKEN
Content-Type: multipart/form-data

file: [插件 jar 包]
```

### 卸载插件

```http
POST /api/plugin/uninstall/{plugin-id}
Authorization: Bearer YOUR_TOKEN
```

### 更新插件

```http
POST /api/plugin/update
Authorization: Bearer YOUR_TOKEN
Content-Type: multipart/form-data

file: [新的插件 jar 包]
request: {
  "id": "plugin-id",
  "remark": "更新说明"
}
```

完整的 API 文档请参考 [api_readme.md](file:///workspace/api_readme.md)。

---

## 常见问题

### 1. 插件安装失败怎么办？

**可能的原因：**
- 插件 jar 包损坏
- 插件版本与 DataEase 版本不兼容
- 权限不足

**解决方法：**
- 重新下载插件包
- 检查插件文档中的版本要求
- 确认使用管理员账号操作
- 查看 `/opt/dataease2.0/logs/` 目录下的日志文件

### 2. 插件安装后不生效？

**解决方法：**
- 重启 DataEase 服务
- 清除浏览器缓存
- 检查插件是否在【插件管理】中显示为已启用

### 3. 如何卸载插件？

**方法一：通过 UI**
- 进入【系统设置】>【插件管理】
- 找到要卸载的插件
- 点击【卸载】按钮

**方法二：手动删除**
```bash
rm /opt/dataease2.0/plugins/your-plugin.jar
# 然后重启服务
```

### 4. 插件开发时如何调试？

1. 在 IDE 中设置断点
2. 使用远程调试连接到 DataEase 服务
3. 查看日志输出
4. 使用 `System.out.println` 或日志框架打印调试信息

### 5. 一个 DataEase 可以安装多少插件？

没有硬性限制，但建议只安装必要的插件以保持系统性能。

---

## 更多资源

- [在线文档](https://dataease.cn/docs/v2/)
- [社区论坛](https://bbs.fit2cloud.com/c/de/6)
- [GitHub Issues](https://github.com/dataease/dataease/issues)
- [API 文档](file:///workspace/api_readme.md)

---

## 更新日志

- **2024-01-01** - 初始版本，支持基础插件安装和开发
