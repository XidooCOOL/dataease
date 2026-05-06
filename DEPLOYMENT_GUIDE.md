# DataEase 服务器部署指南

本文档详细介绍 DataEase 在服务器上的部署方法，包括有宝塔面板和无宝塔面板两种情况。

## 目录

- [环境要求](#环境要求)
- [方式一：无宝塔面板部署](#方式一无宝塔面板部署)
  - [快速安装（推荐）](#快速安装推荐)
  - [离线安装](#离线安装)
  - [Docker Compose 手动部署](#docker-compose-手动部署)
- [方式二：有宝塔面板部署](#方式二有宝塔面板部署)
  - [基础环境准备](#基础环境准备)
  - [安装 Docker 管理器](#安装-docker-管理器)
  - [部署 DataEase](#部署-dataease)
- [反向代理配置](#反向代理配置)
- [安装后配置](#安装后配置)
- [常见问题](#常见问题)

---

## 环境要求

| 项目 | 要求 |
|------|------|
| CPU | 2 核以上 |
| 内存 | 4GB 以上 |
| 磁盘 | 20GB 以上可用空间 |
| 操作系统 | CentOS 7.x / Ubuntu 20.04+ |
| Docker | 20.10.x 或更高版本 |
| Docker Compose | 2.x 或更高版本 |

---

## 方式一：无宝塔面板部署

### 快速安装（推荐）

适合能连接外网的服务器，一键安装最简单：

```bash
# 以 root 用户执行以下命令
curl -sSL https://dataease.oss-cn-hangzhou.aliyuncs.com/quick_start_v2.sh | bash
```

**安装过程会自动：**
- 安装 Docker 和 Docker Compose
- 拉取 DataEase 镜像
- 配置数据库
- 启动所有服务

**安装完成后访问：**
- 地址：`http://服务器IP:8100`
- 用户名：`admin`
- 密码：`DataEase@123456`

---

### 离线安装

适合内网服务器或需要自定义配置的服务器：

#### 1. 下载离线安装包

在有网络的电脑上访问：https://community.fit2cloud.com/#/products/dataease/downloads

下载对应版本的离线安装包，例如：`dataease-offline-installer-v2.x.x-ce.tar.gz`

#### 2. 上传到服务器

```bash
# 使用 scp 或其他工具上传
scp dataease-offline-installer-v2.x.x-ce.tar.gz root@your-server:/tmp/
```

#### 3. 解压并安装

```bash
# SSH 登录到服务器
ssh root@your-server

# 进入安装包目录
cd /tmp

# 解压安装包
tar zxvf dataease-offline-installer-v2.x.x-ce.tar.gz
cd dataease-offline-installer-v2.x.x-ce

# 编辑配置文件（可选）
vim install.conf

# 执行安装
bash install.sh
```

#### 4. 配置说明（install.conf）

```bash
# 基础配置
## 安装目录
DE_BASE=/opt
## Service 端口（访问端口）
DE_PORT=8100
## 安装模式（community=社区版）
DE_INSTALL_MODE=community

# 数据库配置
## 是否使用外部数据库（false=使用内置数据库）
DE_EXTERNAL_MYSQL=false
## 内置数据库密码
DE_MYSQL_PASSWORD=Password123@mysql

# 其他配置
## 导出数据限制
DE_EXPORT_VIEWS_LIMIT=100000
DE_EXPORT_DATASET_LIMIT=100000
```

---

### Docker Compose 手动部署

适合需要深度自定义的高级用户：

#### 1. 安装 Docker 和 Docker Compose

```bash
# 安装 Docker
curl -fsSL https://get.docker.com | bash

# 启动 Docker
systemctl start docker
systemctl enable docker

# 安装 Docker Compose
curl -L "https://github.com/docker/compose/releases/download/v2.16.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

chmod +x /usr/local/bin/docker-compose

# 验证安装
docker --version
docker-compose --version
```

#### 2. 创建数据目录

```bash
mkdir -p /opt/dataease2.0
cd /opt/dataease2.0
```

#### 3. 创建 docker-compose.yml

```yaml
version: '3'

services:
  mysql-de:
    image: mysql:8.0
    container_name: mysql-de
    restart: always
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: Password123@mysql
      MYSQL_DATABASE: dataease
    volumes:
      - ./data/mysql:/var/lib/mysql
      - ./conf/my.cnf:/etc/mysql/conf.d/my.cnf
    networks:
      - dataease-net

  dataease:
    image: registry.fit2cloud.com/dataease/dataease:v2.x.x
    container_name: dataease
    restart: always
    ports:
      - "8100:8100"
    depends_on:
      - mysql-de
    environment:
      DE_MYSQL_HOST: mysql-de
      DE_MYSQL_PORT: 3306
      DE_MYSQL_USER: root
      DE_MYSQL_PASSWORD: Password123@mysql
      DE_MYSQL_DB: dataease
    volumes:
      - ./data/static-resource:/opt/dataease/data/static-resource
      - ./data/map:/opt/dataease/data/map
      - ./data/plugin:/opt/dataease/data/plugin
    networks:
      - dataease-net

networks:
  dataease-net:
    driver: bridge
```

#### 4. 创建配置文件

创建 `conf/my.cnf`：
```ini
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
default-authentication-plugin=mysql_native_password
```

#### 5. 启动服务

```bash
# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

---

## 方式二：有宝塔面板部署

### 基础环境准备

#### 1. 安装宝塔面板

如果还没有安装宝塔面板，请先安装：

```bash
# CentOS
yum install -y wget && wget -O install.sh https://download.bt.cn/install/install_6.0.sh && sh install.sh

# Ubuntu
wget -O install.sh https://download.bt.cn/install/install-ubuntu_6.0.sh && sudo bash install.sh
```

安装完成后，记住宝塔面板的登录地址和密码。

#### 2. 登录宝塔面板

1. 打开浏览器访问宝塔面板地址（通常是 `http://服务器IP:8888`）
2. 使用安装时提供的用户名和密码登录
3. 首次登录需要绑定宝塔账号

#### 3. 安装必要软件

在宝塔面板的【软件商店】中安装以下软件：

| 软件 | 用途 | 备注 |
|------|------|------|
| Docker管理器 | 管理 Docker 容器 | 搜索安装 |
| PM2管理器 | Node.js 进程管理 | 可选 |
| Nginx | 反向代理 | 必需 |
| phpMyAdmin | 数据库管理 | 可选，方便管理数据库 |

**安装步骤：**
1. 点击【软件商店】
2. 搜索"Docker"
3. 点击安装【Docker管理器】
4. 搜索"Nginx"
5. 点击安装【Nginx】

---

### 安装 Docker 管理器

#### 1. 配置 Docker 环境

在终端中执行（可在宝塔面板的【终端】中执行）：

```bash
# 安装 Docker（如果还未安装）
curl -fsSL https://get.docker.com | bash

# 启动 Docker
systemctl start docker
systemctl enable docker

# 安装 Docker Compose
curl -L "https://github.com/docker/compose/releases/download/v2.16.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
```

#### 2. 在宝塔面板中配置 Docker

1. 打开宝塔面板
2. 点击【软件商店】>【Docker管理器】
3. 点击【设置】
4. 配置 Docker 镜像加速（可选，但推荐）：

```json
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com"
  ]
}
```

---

### 部署 DataEase

#### 方法一：使用 Docker 手动部署（推荐）

##### 1. 创建数据目录

在终端中执行：

```bash
mkdir -p /www/dataease
cd /www/dataease
```

##### 2. 创建 docker-compose.yml

在 `/www/dataease/` 目录下创建 `docker-compose.yml` 文件，内容如下：

```yaml
version: '3'

services:
  mysql-de:
    image: mysql:8.0
    container_name: mysql-de
    restart: always
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: Password123@mysql
      MYSQL_DATABASE: dataease
    volumes:
      - ./data/mysql:/var/lib/mysql
      - ./conf/my.cnf:/etc/mysql/conf.d/my.cnf
    networks:
      - dataease-net

  dataease:
    image: registry.fit2cloud.com/dataease/dataease:v2.x.x
    container_name: dataease
    restart: always
    ports:
      - "8100:8100"
    depends_on:
      - mysql-de
    environment:
      DE_MYSQL_HOST: mysql-de
      DE_MYSQL_PORT: 3306
      DE_MYSQL_USER: root
      DE_MYSQL_PASSWORD: Password123@mysql
      DE_MYSQL_DB: dataease
    volumes:
      - ./data/static-resource:/opt/dataease/data/static-resource
      - ./data/map:/opt/dataease/data/map
      - ./data/plugin:/opt/dataease/data/plugin
    networks:
      - dataease-net

networks:
  dataease-net:
    driver: bridge
```

**注意：** 请将 `v2.x.x` 替换为实际版本号，如 `v2.11.0`

##### 3. 创建配置文件

创建 `/www/dataease/conf/my.cnf`：

```ini
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
default-authentication-plugin=mysql_native_password
```

##### 4. 启动服务

```bash
cd /www/dataease

# 创建数据目录
mkdir -p data/mysql data/static-resource data/map data/plugin conf

# 拉取镜像
docker-compose pull

# 启动服务
docker-compose up -d

# 查看状态
docker-compose ps
```

##### 5. 配置防火墙

在宝塔面板中：
1. 点击【安全】>【防火墙】
2. 添加规则：端口 `8100`，协议 `TCP`
3. 点击【确定】

---

#### 方法二：使用宝塔 Docker 图形化部署

##### 1. 下载 DataEase 离线包

在宝塔面板中：
1. 下载 DataEase 离线安装包到本地
2. 上传到服务器 `/tmp/` 目录
3. 解压：`tar zxvf dataease-offline-installer-v2.x.x-ce.tar.gz`

##### 2. 使用 Docker 管理器导入镜像

1. 打开宝塔面板 >【软件商店】>【Docker管理器】
2. 点击【镜像管理】
3. 点击【导入镜像】
4. 选择 `/tmp/dataease-offline-installer-v2.x.x-ce/images/` 目录下的镜像文件
5. 等待镜像导入完成

##### 3. 创建容器

在 Docker 管理器中手动创建容器，或使用命令行方式启动（参考方法一）

---

## 反向代理配置

如果需要使用域名访问或配置 HTTPS，建议使用 Nginx 反向代理。

### 在宝塔面板中配置

#### 1. 添加站点

1. 点击【网站】>【添加站点】
2. 填写域名（如 `dataease.example.com`）
3. 选择 PHP 版本（选择"纯静态"）
4. 点击【提交】

#### 2. 配置反向代理

1. 点击刚刚创建的站点
2. 点击【反向代理】>【添加反向代理】
3. 填写配置：
   - 代理名称：`dataease`
   - 目标URL：`http://127.0.0.1:8100`
   - 发送域名：`$host`
4. 点击【提交】

#### 3. 配置 SSL（可选）

1. 点击【SSL】选项卡
2. 选择【Let's Encrypt】或其他 SSL 证书
3. 开启【强制HTTPS】

### 在无宝塔面板的服务器中配置

创建 Nginx 配置文件 `/etc/nginx/conf.d/dataease.conf`：

```nginx
server {
    listen 80;
    server_name dataease.example.com;

    location / {
        proxy_pass http://127.0.0.1:8100;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # WebSocket 支持
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
```

启用配置：

```bash
nginx -t
systemctl reload nginx
```

---

## 安装后配置

### 1. 首次登录

1. 访问 `http://服务器IP:8100` 或配置的域名
2. 使用默认账号登录：
   - 用户名：`admin`
   - 密码：`DataEase@123456`
3. 首次登录会要求修改密码

### 2. 修改默认密码

建议立即修改默认密码：
1. 点击右上角头像
2. 选择【个人中心】
3. 点击【修改密码】
4. 设置新的强密码

### 3. 配置数据源

1. 点击【数据源】>【新建数据源】
2. 选择数据源类型（MySQL、Excel 等）
3. 填写连接信息
4. 点击【测试连接】
5. 保存数据源

### 4. 创建数据集和图表

1. 进入【数据集】模块
2. 创建新的数据集
3. 选择数据源和表
4. 配置字段
5. 保存数据集
6. 进入【仪表板】
7. 创建新的仪表板
8. 添加图表，选择数据集
9. 拖拽配置图表

### 5. 定时任务配置

如果需要使用定时报告功能，需要配置定时任务：

```bash
# 编辑定时任务
crontab -e

# 添加定时任务（每小时执行一次）
0 * * * * cd /opt/dataease2.0 && docker-compose exec -T dataease java -jar /opt/dataease/task.jar >> logs/task.log 2>&1
```

---

## 常用管理命令

### 服务管理

```bash
# 进入 DataEase 目录
cd /opt/dataease2.0

# 停止服务
docker-compose down

# 启动服务
docker-compose up -d

# 重启服务
docker-compose restart

# 查看日志
docker-compose logs -f dataease

# 查看服务状态
docker-compose ps
```

### 使用 dectl 命令（官方安装包方式）

```bash
# 停止服务
dectl stop

# 启动服务
dectl start

# 重启服务
dectl restart

# 查看状态
dectl status

# 查看日志
dectl logs

# 升级
dectl upgrade

# 卸载
dectl uninstall
```

---

## 常见问题

### 1. 安装失败，提示磁盘空间不足

**解决方法：**
```bash
# 查看磁盘空间
df -h

# 清理磁盘
docker system prune -a

# 扩展磁盘或清理大文件
```

### 2. Docker 镜像拉取失败

**解决方法：**
- 配置 Docker 镜像加速器
- 检查网络连接
- 使用离线安装包

### 3. 数据库连接失败

**解决方法：**
```bash
# 检查 MySQL 容器状态
docker-compose ps mysql-de

# 查看 MySQL 日志
docker-compose logs mysql-de

# 重启 MySQL
docker-compose restart mysql-de
```

### 4. 端口被占用

**解决方法：**
修改 `docker-compose.yml` 中的端口映射：

```yaml
ports:
  - "8101:8100"  # 使用 8101 端口访问
```

### 5. 忘记 admin 密码

**解决方法：**
```bash
# 进入 MySQL 容器
docker exec -it mysql-de mysql -uroot -pPassword123@mysql

# 选择数据库
use dataease;

# 重置 admin 密码（MD5 加密）
UPDATE sys_user SET password = '018c6a07f76a84d706d82f7c1bb1dc28' WHERE username = 'admin';

# 退出
exit;
```

重置后密码为：`DataEase@123456`

### 6. 如何升级 DataEase

**方法一：使用官方升级脚本**
```bash
# 下载新版本离线包
tar zxvf dataease-offline-installer-v2.x.x-ce.tar.gz
cd dataease-offline-installer-v2.x.x-ce
bash install.sh
```

**方法二：手动升级 Docker 镜像**
```bash
# 拉取新版本镜像
docker-compose pull

# 重启服务
docker-compose up -d
```

---

## 数据备份

### 1. 备份数据库

```bash
docker exec mysql-de mysqldump -uroot -pPassword123@mysql dataease > backup_$(date +%Y%m%d).sql
```

### 2. 备份配置文件

```bash
tar czvf dataease_backup_$(date +%Y%m%d).tar.gz /opt/dataease2.0/data /opt/dataease2.0/conf
```

### 3. 恢复数据

```bash
# 恢复数据库
docker exec -i mysql-de mysql -uroot -pPassword123@mysql dataease < backup_20240101.sql

# 恢复配置
tar xzvf dataease_backup_20240101.tar.gz -C /
```

---

## 技术支持

- 在线文档：https://dataease.cn/docs/v2/
- 社区论坛：https://bbs.fit2cloud.com/c/de/6
- GitHub Issues：https://github.com/dataease/dataease/issues
- 微信交流群：[扫码加入](https://github.com/dataease/dataease#微信交流群)

---

**注意：** 本文档中的命令和配置可能因版本不同而有所差异，请根据实际版本进行调整。建议在生产环境部署前先在测试环境中验证。
