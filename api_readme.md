# DataEase API 接口文档

## 目录

1. [概述](#概述)
2. [认证与登录](#认证与登录)
3. [用户与权限管理](#用户与权限管理)
4. [组织与角色管理](#组织与角色管理)
5. [数据源管理](#数据源管理)
6. [数据集管理](#数据集管理)
7. [可视化与仪表板](#可视化与仪表板)
8. [图表与报表](#图表与报表)
9. [模板与市场](#模板与市场)
10. [第三方集成](#第三方集成)
11. [系统设置](#系统设置)
12. [其他模块](#其他模块)

---

## 概述

DataEase 是人人可用的开源 BI 工具，帮助用户快速分析数据并洞察业务趋势。本 API 文档提供了 DataEase 所有接口的详细说明，方便开发者进行二次开发和集成。

**基本信息：**
- 基础路径：`/api`（实际部署路径可能有所不同）
- 认证方式：Token 认证（登录获取）
- 数据格式：JSON
- 字符编码：UTF-8

---

## 认证与登录

### 1. 登录接口 (LoginApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/login/localLogin` | POST | 本地登录 |
| `/login/refresh` | GET | Token 续命（隐藏） |
| `/login/platformLogin/{origin}` | POST | 第三方登录（隐藏） |
| `/logout` | GET | 登出系统 |
| `/mfa/qr/{id}` | POST | 获取 MFA 二维码 |
| `/mfa/login` | POST | MFA 双因素认证登录 |
| `/login/modifyInvalidPwd` | POST | 修改无效密码（隐藏） |

**本地登录示例请求：**
```json
{
  "username": "admin",
  "password": "DataEase@123456"
}
```

**响应示例：**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "admin"
}
```

---

### 2. 权限管理接口 (AuthApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/busiResource/{flag}` | GET | 查询业务资源树 |
| `/busiPermission` | POST | 查询对象已授权资源 |
| `/busiTargetPermission` | POST | 查询资源已授权对象 |
| `/menuResource` | GET | 查询菜单树 |
| `/menuPermission` | POST | 查询对象已授权菜单 |
| `/menuTargetPermission` | POST | 查询菜单已授权对象 |
| `/saveBusiPer` | POST | 保存资源权限 |
| `/saveBusiTargetPer` | POST | 资源维度保存权限 |
| `/saveMenuPer` | POST | 保存菜单权限 |
| `/saveMenuTargetPer` | POST | 菜单维度保存权限 |

---

## 用户与权限管理

### 3. 用户管理接口 (UserApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/user/pager/{goPage}/{pageSize}` | POST | 分页查询用户列表 |
| `/user/queryById/{id}` | GET | 查询用户详情 |
| `/user/personInfo` | GET | 查询当前登录用户个人信息 |
| `/user/personSysVariableInfo/{id}` | GET | 查询用户系统变量信息 |
| `/user/ipInfo` | GET | 查询客户端IP信息 |
| `/user/create` | POST | 创建用户 |
| `/user/createPlatform` | POST | 创建第三方用户 |
| `/user/edit` | POST | 编辑用户信息 |
| `/user/personEdit` | POST | 修改个人信息 |
| `/user/delete/{id}` | POST | 删除用户 |
| `/user/batchDel` | POST | 批量删除用户 |
| `/user/role/option` | POST | 查询角色可绑定用户 |
| `/user/org/option` | GET | 查询组织内用户 |
| `/user/role/selected/{goPage}/{pageSize}` | POST | 分页查询角色已绑定用户 |
| `/user/switch/{oId}` | POST | 切换组织 |
| `/user/info` | GET | 获取当前登录人信息 |
| `/user/byCurOrg` | POST | 查询当前组织内用户 |
| `/user/switchLanguage` | POST | 切换语言 |
| `/user/excelTemplate` | POST | 下载批量导入模板 |
| `/user/batchImport` | POST | 批量导入用户 |
| `/user/errorRecord/{key}` | GET | 下载批量导入失败记录 |
| `/user/clearErrorRecord/{key}` | GET | 清理批量导入失败记录 |
| `/user/defaultPwd` | GET | 查询默认密码 |
| `/user/resetPwd/{id}` | POST | 重置为默认密码 |
| `/user/enable` | POST | 切换用户状态 |
| `/user/modifyPwd` | POST | 修改个人密码 |
| `/user/queryByAccount/{account}` | GET | 根据账号查询用户 |
| `/user/unBind/{origin}` | POST | 解除第三方绑定 |
| `/user/bindStatus` | GET | 查询绑定状态 |
| `/user/mfaQr` | GET | 获取MFA二维码 |
| `/user/mfabound` | GET | 查询MFA绑定状态 |
| `/user/mfaBind` | POST | 绑定MFA |
| `/user/mfaUnbind/{code}` | POST | 解绑MFA |
| `/user/mfaRest/{id}` | POST | 重置MFA绑定状态 |

**创建用户示例：**
```json
{
  "username": "testuser",
  "name": "测试用户",
  "email": "test@example.com",
  "password": "Password123",
  "roleIds": [1, 2]
}
```

---

### 4. 系统变量接口 (SysVariablesApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/sysVariables/` | GET/POST | 系统变量管理（具体路径需参考实现） |

---

### 5. API Key 管理接口 (ApiKeyApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/apikey/` | GET/POST | API Key 管理（具体路径需参考实现） |

---

### 6. 嵌入配置接口 (EmbeddedApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/embedded/` | GET/POST | 嵌入配置管理（具体路径需参考实现） |

---

## 组织与角色管理

### 7. 组织管理接口 (OrgApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/org/page/tree` | POST | 查询组织树 |
| `/org/page/lazyTree` | POST | 懒加载组织树 |
| `/org/page/create` | POST | 创建组织 |
| `/org/page/edit` | POST | 编辑组织 |
| `/org/page/delete/{id}` | POST | 删除组织 |
| `/org/mounted` | POST | 查询权限内组织树 |
| `/org/lazyMounted` | POST | 查询权限内组织树（懒加载） |
| `/org/resourceExist/{oid}` | GET | 检查资源是否存在（隐藏） |
| `/org/detail/{oid}` | GET | 查询组织详情（隐藏） |
| `/org/subOrgs` | GET | 查询子组织列表（隐藏） |

**创建组织示例：**
```json
{
  "name": "研发部",
  "pid": 0,
  "description": "技术研发部门"
}
```

---

### 8. 角色管理接口 (RoleApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/role/query` | POST | 查询角色列表 |
| `/role/create` | POST | 创建角色 |
| `/role/edit` | POST | 编辑角色 |
| `/role/mountUser` | POST | 绑定用户到角色 |
| `/role/mountExternalUser` | POST | 绑定组织外用户 |
| `/role/searchExternalUser/{keyword}` | GET | 搜索组织外用户 |
| `/role/unMountUser` | POST | 解绑角色用户 |
| `/role/user/option` | POST | 查询用户可选角色 |
| `/role/user/selected` | POST | 查询用户已选角色 |
| `/role/detail/{rid}` | GET | 查询角色详情 |
| `/role/delete/{rid}` | POST | 删除角色 |
| `/role/beforeUnmountInfo` | POST | 解绑用户前确认 |
| `/role/copy` | POST | 复制角色（隐藏） |
| `/role/byCurOrg` | POST | 查询当前组织内角色 |

---

## 数据源管理

### 9. 数据源管理接口 (DatasourceApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasource/query/{keyWord}` | GET | 查询数据源列表 |
| `/datasource/save` | POST | 保存数据源 |
| `/datasource/update` | POST | 更新数据源 |
| `/datasource/move` | POST | 移动数据源 |
| `/datasource/reName` | POST | 重命名数据源 |
| `/datasource/createFolder` | POST | 新建文件夹 |
| `/datasource/checkRepeat` | POST | 校验数据源名称重复 |
| `/datasource/types` | GET | 获取支持的数据源类型列表 |
| `/datasource/validate` | POST | 校验数据源连接 |
| `/datasource/getSchema` | POST | 获取数据库Schema |
| `/datasource/validate/{datasourceId}` | GET | 校验指定数据源连接 |
| `/datasource/perDelete/{datasourceId}` | POST | 检查数据源是否可删除 |
| `/datasource/delete/{datasourceId}` | GET | 删除数据源 |
| `/datasource/get/{datasourceId}` | GET | 获取数据源详情 |
| `/datasource/hidePw/{datasourceId}` | GET | 获取数据源详情（隐藏密码） |
| `/datasource/getSimpleDs/{datasourceId}` | GET | 获取数据源简单信息 |
| `/datasource/getTableField` | POST | 获取表字段信息 |
| `/datasource/syncApiTable` | POST | 同步API数据表 |
| `/datasource/syncApiDs` | POST | 同步API数据源 |
| `/datasource/tree` | POST | 获取数据源树 |
| `/datasource/getTables` | POST | 获取数据源中的表列表 |
| `/datasource/getTableStatus` | POST | 获取数据表更新状态 |
| `/datasource/checkApiDatasource` | POST | 校验API数据源 |
| `/datasource/uploadFile` | POST | 上传文件（Excel等） |
| `/datasource/previewData` | POST | 预览数据 |
| `/datasource/latestUse` | POST | 查询最近使用的数据源 |
| `/datasource/showFinishPage` | GET | 是否显示完成页面 |
| `/datasource/setShowFinishPage` | POST | 设置是否显示完成页面 |
| `/datasource/listSyncRecord/{dsId}/{goPage}/{pageSize}` | POST | 分页查询同步记录日志 |
| `/datasource/simple/{id}` | GET | 获取数据源简单VO |
| `/datasource/multidimensionalTables` | POST | 获取多维表格列表 |
| `/datasource/loadRemoteFile` | POST | 加载远程文件 |

**MySQL 数据源配置示例：**
```json
{
  "name": "MySQL 生产库",
  "type": "mysql",
  "host": "192.168.1.100",
  "port": 3306,
  "username": "root",
  "password": "password",
  "database": "mydb"
}
```

---

### 10. 数据源驱动管理接口 (DatasourceDriverApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasourceDriver/` | GET/POST | 数据源驱动管理（具体路径需参考实现） |

---

### 11. 数据引擎接口 (EngineApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/engine/` | GET/POST | 数据引擎管理（具体路径需参考实现） |

---

## 数据集管理

### 12. 数据集表管理接口 (DatasetTableApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasetTable/save` | POST | 保存字段 |
| `/datasetTable/get/{id}` | POST | 查询字段详情 |
| `/datasetTable/listByDatasetGroup/{id}` | POST | 获取数据集字段列表 |
| `/datasetTable/listByDsIds` | POST | 批量获取数据集字段Map |
| `/datasetTable/delete/{id}` | POST | 删除字段 |
| `/datasetTable/listByDQ/{id}` | POST | 获取字段分组（维度/指标） |
| `/datasetTable/copilotFields/{id}` | POST | 获取Copilot字段分组 |
| `/datasetTable/listWithPermissions/{id}` | GET | 获取字段（带权限） |
| `/datasetTable/multFieldValuesForPermissions` | POST | 获取字段枚举值（带权限） |
| `/datasetTable/getFunction` | POST | 获取计算字段函数列表 |
| `/datasetTable/deleteByChartId/{id}` | POST | 删除图表计算字段（隐藏） |

---

### 13. 数据集数据接口 (DatasetDataApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasetData/previewData` | POST | 预览数据 |
| `/datasetData/tableField` | POST | 获取数据集节点字段 |
| `/datasetData/previewSql` | POST | SQL预览 |
| `/datasetData/previewSqlCheck` | POST | SQL片段校验（隐藏） |
| `/datasetData/enumValueDs` | POST | 数据集获取字段枚举值 |
| `/datasetData/enumValue` | POST | 获取字段枚举值 |
| `/datasetData/enumValueObj` | POST | 获取字段枚举值（多字段） |
| `/datasetData/getDatasetCount` | POST | 获取数据集总数据量（隐藏） |
| `/datasetData/getDatasetTotal` | POST | 获取数据集数据量（隐藏） |
| `/datasetData/getFieldTree` | POST | 获取下拉树数据（隐藏） |

---

### 14. 数据集树接口 (DatasetTreeApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasetTree/` | GET/POST | 数据集树管理（具体路径需参考实现） |

---

### 15. 数据助手接口 (DataAssistantApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/dataAssistant/` | GET/POST | 数据助手相关（具体路径需参考实现） |

---

### 16. 数据集SQL日志接口 (DatasetTableSqlLogApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/datasetTableSqlLog/` | GET/POST | SQL日志管理（具体路径需参考实现） |

---

### 17. 数据列权限接口 (ColumnPermissionsApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/columnPermissions/` | GET/POST | 列权限管理（具体路径需参考实现） |

---

### 18. 数据行权限接口 (RowPermissionsApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/rowPermissions/` | GET/POST | 行权限管理（具体路径需参考实现） |

---

## 可视化与仪表板

### 19. 可视化管理接口 (DataVisualizationApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/dataVisualization/findById` | POST | 查询可视化资源 |
| `/dataVisualization/findCopyResource/{dvId}/{busiFlag}` | GET | 查询临时复制资源 |
| `/dataVisualization/saveCanvas` | POST | 保存画布 |
| `/dataVisualization/appCanvasNameCheck` | POST | 应用名称检查 |
| `/dataVisualization/checkCanvasChange` | POST | 检查画布是否有变动 |
| `/dataVisualization/updateCanvas` | POST | 更新画布 |
| `/dataVisualization/updatePublishStatus` | POST | 更新发布状态 |
| `/dataVisualization/recoverToPublished` | POST | 恢复到发布版本 |
| `/dataVisualization/updateBase` | POST | 更新可视化资源基础信息 |
| `/dataVisualization/deleteLogic/{dvId}/{busiFlag}` | POST | 逻辑删除可视化资源 |
| `/dataVisualization/tree` | POST | 查询可视化资源树 |
| `/dataVisualization/interactiveTree` | POST | 查询业务资源树 |
| `/dataVisualization/move` | POST | 移动可视化资源 |
| `/dataVisualization/nameCheck` | POST | 名称校验 |
| `/dataVisualization/findRecent` | POST | 查询最近操作的资源 |
| `/dataVisualization/copy` | POST | 复制可视化资源 |
| `/dataVisualization/findDvType/{dvId}` | GET | 查询可视化资源类型 |
| `/dataVisualization/updateCheckVersion/{dvId}` | GET | 更新校验版本 |
| `/dataVisualization/decompression` | POST | 解析可视化资源模板信息 |
| `/dataVisualization/decompressionLocalFile` | POST | 解析本地上传的模板文件 |
| `/dataVisualization/viewDetailList/{dvId}` | GET | 查询仪表板视图明细列表 |
| `/dataVisualization/export2AppCheck` | POST | 导出应用检查 |
| `/dataVisualization/exportLogApp` | POST | 记录导出应用模板日志 |
| `/dataVisualization/exportLogTemplate` | POST | 记录导出样式模板日志 |
| `/dataVisualization/exportLogPDF` | POST | 记录导出PDF日志 |
| `/dataVisualization/exportLogImg` | POST | 记录导出图片日志 |

---

### 20. 可视化主题接口 (VisualizationSubjectApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationSubject/` | GET/POST | 主题管理（具体路径需参考实现） |

---

### 21. 可视化联动接口 (VisualizationLinkageApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationLinkage/` | GET/POST | 联动配置管理（具体路径需参考实现） |

---

### 22. 可视化跳转接口 (VisualizationLinkJumpApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationLinkJump/` | GET/POST | 跳转配置管理（具体路径需参考实现） |

---

### 23. 可视化背景接口 (VisualizationBackgroundApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationBackground/` | GET/POST | 背景管理（具体路径需参考实现） |

---

### 24. 可视化水印接口 (VisualizationWatermarkApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationWatermark/` | GET/POST | 水印管理（具体路径需参考实现） |

---

### 25. 可视化存储接口 (VisualizationStoreApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationStore/` | GET/POST | 存储管理（具体路径需参考实现） |

---

### 26. 可视化外部参数接口 (VisualizationOuterParamsApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/visualizationOuterParams/` | GET/POST | 外部参数管理（具体路径需参考实现） |

---

### 27. 静态资源接口 (StaticResourceApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/staticResource/` | GET/POST | 静态资源管理（具体路径需参考实现） |

---

## 图表与报表

### 28. 图表视图管理接口 (ChartViewApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/chartView/getChart/{id}` | POST | 查询图表详情并计算数据（隐藏） |
| `/chartView/listByDQ/{id}/{chartId}` | POST | 获取图表字段（维度/指标分组） |
| `/chartView/save` | POST | 保存图表 |
| `/chartView/checkSameDataSet/{viewIdSource}/{viewIdTarget}` | GET | 检查两个视图是否使用相同数据集 |
| `/chartView/getDetail/{id}/{resourceTable}` | POST | 查询图表详情 |
| `/chartView/viewOption/{resourceId}` | GET | 查询仪表板下的视图选项 |
| `/chartView/copyField/{id}/{chartId}` | POST | 复制视图字段 |
| `/chartView/deleteField/{id}` | POST | 删除视图字段 |
| `/chartView/deleteFieldByChart/{chartId}` | POST | 清空当前视图的计算字段 |
| `/chartView/chartBaseInfo/{id}/{resourceTable}` | GET | 获取视图头部信息 |

**创建图表示例：**
```json
{
  "title": "销售趋势图",
  "type": "line",
  "datasetId": 1,
  "xAxis": ["date"],
  "yAxis": ["sales"],
  "filters": []
}
```

---

### 29. 图表数据接口 (ChartDataApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/chartData/getData` | POST | 获取图表数据 |
| `/chartData/innerExportDetails` | POST | 导出图表数据 |
| `/chartData/innerExportDataSetDetails` | POST | 导出数据集明细数据 |
| `/chartData/getFieldData/{fieldId}/{fieldType}` | POST | 获取字段值列表 |
| `/chartData/getDrillFieldData/{fieldId}` | POST | 获取下钻字段值 |

---

### 30. 定时报告接口 (ReportApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/report/pager/{goPage}/{pageSize}` | POST | 分页查询报告任务列表 |
| `/report/create` | POST | 创建报告任务 |
| `/report/update` | POST | 更新报告任务 |
| `/report/fireNow/{taskId}` | POST | 立即运行报告任务 |
| `/report/stop/{taskId}` | POST | 停止报告任务 |
| `/report/start/{taskId}` | POST | 启用报告任务 |
| `/report/delete` | POST | 删除报告任务 |
| `/report/info/{taskId}` | GET | 查询报告任务详情 |
| `/report/logPager/{goPage}/{pageSize}` | POST | 分页查询报告执行日志 |
| `/report/deleteLog` | POST | 删除报告执行日志 |
| `/report/logMsg` | POST | 获取日志错误信息 |
| `/report/export` | POST | 导出报告 |

---

## 模板与市场

### 31. 模板管理接口 (TemplateManageApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/templateManage/templateList` | POST | 查询模板列表 |
| `/templateManage/save` | POST | 保存模板 |
| `/templateManage/delete/{id}/{categoryId}` | POST | 删除模板 |
| `/templateManage/deleteCategory/{id}` | POST | 删除模板分类 |
| `/templateManage/findOne/{templateId}` | GET | 查询模板明细 |
| `/templateManage/findCategoriesByTemplateIds` | POST | 根据模板ID查询分类 |
| `/templateManage/find` | POST | 查询模板 |
| `/templateManage/findCategories` | POST | 查询模板分类列表 |
| `/templateManage/nameCheck` | POST | 模板名称校验 |
| `/templateManage/categoryTemplateNameCheck` | POST | 分类名称校验 |
| `/templateManage/checkCategoryTemplateBatchNames` | POST | 分类名称批量校验 |
| `/templateManage/batchUpdate` | POST | 批量更新模板 |
| `/templateManage/batchDelete` | POST | 批量删除模板 |

---

### 32. 模板市场接口 (TemplateMarketApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/templateMarket/` | GET/POST | 模板市场相关（具体路径需参考实现） |

---

## 分享与协作

### 33. 分享管理接口 (XpackShareApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/xpackShare/status/{resourceId}` | GET | 查询资源分享状态 |
| `/xpackShare/switcher/{resourceId}` | POST | 切换资源分享状态 |
| `/xpackShare/editExp` | POST | 设置分享有效期 |
| `/xpackShare/editPwd` | POST | 编辑分享密码 |
| `/xpackShare/detail/{resourceId}` | GET | 查询分享详情 |
| `/xpackShare/query` | POST | 查询分享列表 |
| `/xpackShare/proxyInfo` | POST | 查询分享代理信息 |
| `/xpackShare/validate` | POST | 验证分享密码 |
| `/xpackShare/editUuid` | POST | 编辑分享UUID |

---

### 34. 分享票据接口 (ShareTicketApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/shareTicket/` | GET/POST | 分享票据管理（具体路径需参考实现） |

---

### 35. 协作交流接口 (CommunicateApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/communicate/` | GET/POST | 协作交流相关（具体路径需参考实现） |

---

## 第三方集成

### 36. 飞书集成接口 (LarkApi/LarksuiteApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/lark/` | GET/POST | 飞书集成相关（具体路径需参考实现） |
| `/larksuite/` | GET/POST | 飞书套件集成（具体路径需参考实现） |

---

### 37. 钉钉集成接口 (DingtalkApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/dingtalk/` | GET/POST | 钉钉集成相关（具体路径需参考实现） |

---

### 38. 企业微信集成接口 (WecomApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/wecom/` | GET/POST | 企业微信集成（具体路径需参考实现） |

---

### 39. 邮件接口 (EmailApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/email/` | GET/POST | 邮件相关（具体路径需参考实现） |

---

### 40. Webhook 接口 (WebhookApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/webhook/` | GET/POST | Webhook 相关（具体路径需参考实现） |

---

## 系统设置

### 41. 系统参数接口 (SysParameterApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/sysParameter/singleVal/{key}` | GET | 查询单个配置值 |
| `/sysParameter/saveOnlineMap` | POST | 保存在线地图配置 |
| `/sysParameter/queryOnlineMap` | GET | 查询在线地图配置 |
| `/sysParameter/queryOnlineMap/{type}` | GET | 按类型查询在线地图配置 |
| `/sysParameter/basic/query` | GET | 查询基础设置（非X-Pack） |
| `/sysParameter/basic/save` | POST | 保存基础设置（非X-Pack） |
| `/sysParameter/requestTimeOut` | GET | 查询请求超时时间 |
| `/sysParameter/defaultSettings` | GET | 查询系统默认配置 |
| `/sysParameter/ui` | GET | UI配置（隐藏） |
| `/sysParameter/defaultLogin` | GET | 默认登录方式（隐藏） |
| `/sysParameter/shareBase` | GET | 查询分享基础设置 |
| `/sysParameter/i18nOptions` | GET | 查询自定义国际化选项 |
| `/sysParameter/sqlbot` | GET | 查询SQLBot嵌入配置 |
| `/sysParameter/sqlbot` | POST | 保存SQLBot嵌入配置 |

---

### 42. 系统信息接口 (SystemInfoApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/systemInfo/` | GET/POST | 系统信息管理（具体路径需参考实现） |

---

### 43. 地图接口 (MapApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/map/worldTree` | GET | 获取世界地图区域树 |

---

### 44. 地理信息接口 (GeoApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/geo/` | GET/POST | 地理信息管理（具体路径需参考实现） |

---

### 45. 自定义地理信息接口 (CustomGeoApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/customGeo/` | GET/POST | 自定义地理信息（具体路径需参考实现） |

---

### 46. 菜单接口 (MenuApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/menu/` | GET/POST | 菜单管理（具体路径需参考实现） |

---

### 47. 字体接口 (FontApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/font/` | GET/POST | 字体管理（具体路径需参考实现） |

---

### 48. 许可证接口 (LicenseApi)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/license/` | GET/POST | 许可证管理（具体路径需参考实现） |

---

## 其他模块

### 49. X-Pack 高级功能

| 接口模块 | 说明 |
|---------|------|
| XpackComponentApi | X-Pack组件管理 |
| XpackAppearanceApi | 外观设置 |
| XpackAuthenticationApi | 认证设置 |
| XpackOauth2Api | OAuth2 设置 |
| XpackSaml2Api | SAML2 设置 |
| PluginApi | 插件管理 |
| DataFillingApi | 数据填报 |

---

### 50. 数据同步模块

| 接口模块 | 说明 |
|---------|------|
| TaskApi | 同步任务管理 |
| TaskLogApi | 同步任务日志 |
| SummaryApi | 同步摘要 |
| SyncDatasourceApi | 同步数据源管理 |

---

### 51. 导出与导出中心

| 接口模块 | 说明 |
|---------|------|
| BaseExportApi | 基础导出功能 |
| ExportCenterApi | 导出中心 |

---

### 52. 阈值与告警

| 接口模块 | 说明 |
|---------|------|
| ThresholdApi | 阈值告警管理 |

---

### 53. 日志与消息

| 接口模块 | 说明 |
|---------|------|
| LogApi | 系统日志 |
| MsgCenterApi | 消息中心 |

---

### 54. AI 组件

| 接口模块 | 说明 |
|---------|------|
| AiComponentApi | AI 组件管理 |

---

### 55. 自由表接口 (FreeApi)

| 接口模块 | 说明 |
|---------|------|
| FreeApi | 自由表/自由关系管理 |

---

## 通用响应格式

### 成功响应
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 错误响应
```json
{
  "code": 400,
  "message": "错误信息",
  "data": null
}
```

---

## 错误码说明

| 错误码 | 说明 |
|-------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证或Token过期 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

---

## 附录

### 数据源类型列表

- MySQL
- Oracle
- SQL Server
- PostgreSQL
- MariaDB
- Db2
- TiDB
- MongoDB-BI
- ClickHouse
- Apache Doris
- Apache Impala
- StarRocks
- Amazon RedShift
- Excel
- CSV
- API 数据源

---

### 图表类型

- 折线图
- 柱状图
- 饼图
- 环形图
- 面积图
- 散点图
- 地图
- 仪表盘
- 表格
- 等等

---

## 联系方式

- 在线文档：https://dataease.cn/docs/v2/
- 社区论坛：https://bbs.fit2cloud.com/c/de/6
- GitHub：https://github.com/dataease/dataease
- Gitee：https://gitee.com/fit2cloud-feizhiyun/DataEase

---

**注意：** 本 API 文档中的具体路径和参数可能需要根据实际部署版本进行调整。建议在开发前使用 Knife4j/Swagger 文档进行确认。

