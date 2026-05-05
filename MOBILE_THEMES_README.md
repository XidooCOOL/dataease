# DataEase 移动端主题设计方案

## 📋 项目概览

本设计方案为 DataEase 提供多套基于成熟 UI 库的手机端主题方案，支持主题实时切换、持久化存储和深色模式。

## 🎨 主题方案总览

| 主题名称 | 类型 | 主色调 | 适用场景 |
|---------|------|--------|---------|
| 浅色主题 | light | #3370FF | 默认通用场景 |
| 深色主题 | dark | #4F7DFE | 夜间/省电模式 |
| 商务蓝 | blue | #1E40AF | 企业正式场景 |
| 神秘紫 | purple | #7C3AED | 创意/个性化场景 |
| 清新绿 | green | #059669 | 健康/环保主题 |
| 活力橙 | orange | #EA580C | 营销/促销场景 |
| 少女粉 | pink | #EC4899 | 女性/时尚场景 |
| 渐变主题 | gradient | #667EEA | 科技/现代场景 |

## 📁 文件结构

```
core/core-frontend/src/theme/mobile/
├── index.ts                           # 主题入口文件
├── themes.ts                          # 主题配置定义
├── useMobileTheme.ts                  # 主题管理 Hook
├── elementPlusMobile.ts               # Element Plus Mobile 主题
├── antDesignMobile.ts                # Ant Design Mobile 主题
├── components/
│   ├── ThemeSwitcher.vue             # 主题切换组件（悬浮按钮）
│   ├── ThemePicker.vue               # 主题选择器（弹窗）
│   └── ThemePreview.vue              # 主题预览缩略图组件
├── examples/
│   └── ThemePreviewPage.vue          # 主题预览页面示例
└── styles/
    └── theme-variables.css            # 全局样式变量
```

## 🚀 快速开始

### 1. 安装依赖

项目已集成 Vant，无需额外安装。如需使用其他 UI 库：

```bash
# Element Plus Mobile
npm install element-plus @element-plus/relaxed

# Ant Design Mobile
npm install antd-mobile
```

### 2. 引入主题样式

在入口文件中引入主题样式：

```typescript
// main.ts
import { createApp } from 'vue'
import App from './App.vue'
import 'vant/lib/index.css'
import '@/theme/mobile/styles/theme-variables.css' // 新增

const app = createApp(App)
app.mount('#app')
```

### 3. 使用主题 Hook

```vue
<template>
  <div class="mobile-layout">
    <van-nav-bar title="仪表板" />
    
    <!-- 主题切换按钮 -->
    <ThemeSwitcher />
    
    <div class="mobile-content">
      <!-- 仪表板内容 -->
    </div>
  </div>
</template>

<script setup lang="ts">
import { ThemeSwitcher } from '@/theme/mobile'
</script>
```

## 🎯 核心功能

### 1. 主题配置系统

#### themes.ts - 主题定义

每个主题包含以下配置：

```typescript
export interface ThemeConfig {
  name: string              // 主题显示名称
  type: ThemeType          // 主题类型标识
  colors: {                // 颜色配置
    primary: string        // 主色
    primaryLight: string   // 主色浅色
    primaryDark: string    // 主色深色
    success: string        // 成功色
    warning: string        // 警告色
    danger: string         // 危险色
    info: string           // 信息色
    text: string           // 主文本色
    textSecondary: string  // 次要文本色
    background: string     // 背景色
    backgroundLight: string // 浅色背景
    border: string         // 边框色
    cardBackground: string  // 卡片背景
    navBarBackground: string // 导航栏背景
    tabBarBackground: string // 标签栏背景
  }
  shadows: {               // 阴影配置
    sm: string            // 小阴影
    md: string            // 中阴影
    lg: string            // 大阴影
  }
  radii: {                // 圆角配置
    sm: string            // 小圆角
    md: string            // 中圆角
    lg: string            // 大圆角
    full: string          // 全圆角
  }
}
```

### 2. 主题管理 Hook

#### useMobileTheme.ts - 主题状态管理

```typescript
import { useMobileTheme } from '@/theme/mobile'

export default {
  setup() {
    const { 
      currentThemeType,      // 当前主题类型
      currentTheme,          // 当前主题配置
      setTheme,              // 设置主题方法
      getThemeList           // 获取主题列表
    } = useMobileTheme()
    
    // 切换到深色主题
    setTheme('dark')
    
    // 获取所有可用主题
    const themes = getThemeList()
    console.log(themes)
    // [
    //   { type: 'light', name: '浅色主题', preview: '#3370FF' },
    //   { type: 'dark', name: '深色主题', preview: '#1F2937' },
    //   ...
    // ]
    
    return { currentThemeType, currentTheme }
  }
}
```

### 3. 主题预览组件

#### ThemePreview.vue - 主题预览缩略图

单个主题预览组件，展示主题的颜色和布局效果：

```vue
<template>
  <ThemePreview 
    :theme="currentTheme" 
    :is-active="true"
  />
</template>

<script setup lang="ts">
import { getThemeConfig } from '@/theme/mobile'
import ThemePreview from '@/theme/mobile/components/ThemePreview.vue'

const currentTheme = getThemeConfig('light')
</script>
```

#### ThemePicker.vue - 主题选择器

完整的主题选择弹窗，包含所有主题预览和智能切换设置：

```vue
<template>
  <ThemePicker 
    @close="showPicker = false" 
    @change="handleThemeChange"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ThemePicker from '@/theme/mobile/components/ThemePicker.vue'

const showPicker = ref(false)
const handleThemeChange = (theme) => {
  console.log('Theme changed:', theme)
}
</script>
```

#### ThemeSwitcher.vue - 主题切换按钮

悬浮主题切换按钮，点击弹出主题选择器：

```vue
<template>
  <ThemeSwitcher />
</template>

<script setup>
import { ThemeSwitcher } from '@/theme/mobile'
</script>
```

### 4. 主题预览页面示例

完整的主题设置页面，包含当前主题预览、所有主题选择和智能切换设置：

```vue
<template>
  <ThemePreviewPage />
</template>

<script setup lang="ts">
import ThemePreviewPage from '@/theme/mobile/examples/ThemePreviewPage.vue'
</script>
```

## 🎨 使用主题变量

### CSS 变量方式

```vue
<template>
  <div class="dashboard-card">
    <h2 class="title">销售报表</h2>
    <div class="chart-container">
      <!-- 图表内容 -->
    </div>
  </div>
</template>

<style scoped>
.dashboard-card {
  background-color: var(--mobile-card-bg);
  border-radius: var(--mobile-radius-lg);
  padding: var(--mobile-space-lg);
  box-shadow: var(--mobile-shadow-md);
}

.title {
  color: var(--mobile-text);
  font-size: var(--mobile-font-size-lg);
  font-weight: 600;
}

.chart-container {
  background-color: var(--mobile-background-light);
  border-radius: var(--mobile-radius-md);
  padding: var(--mobile-space-md);
}
</style>
```

### JavaScript 方式

```typescript
import { mobileThemeVars } from '@/theme/mobile'

export default {
  setup() {
    const themeVars = mobileThemeVars.value
    
    // 获取主色
    console.log(themeVars.primary) // #3370FF
    
    // 获取文本色
    console.log(themeVars.text) // #1F2329
    
    // 获取圆角
    console.log(themeVars.radiusMd) // 8px
    
    return { themeVars }
  }
}
```

## 📱 移动端组件示例

### 基础卡片

```vue
<template>
  <div class="mobile-card">
    <div class="mobile-card__title">{{ title }}</div>
    <div class="mobile-card__content">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped>
.mobile-card {
  background-color: var(--mobile-card-bg);
  border-radius: var(--mobile-radius-lg);
  padding: var(--mobile-space-lg);
  margin-bottom: var(--mobile-space-lg);
  box-shadow: var(--mobile-shadow-md);
}

.mobile-card__title {
  font-size: var(--mobile-font-size-lg);
  font-weight: 600;
  color: var(--mobile-text);
  margin-bottom: var(--mobile-space-md);
}

.mobile-card__content {
  font-size: var(--mobile-font-size-md);
  color: var(--mobile-text-secondary);
  line-height: 1.6;
}
</style>
```

### 列表项

```vue
<template>
  <div class="mobile-list">
    <div 
      v-for="item in items" 
      :key="item.id"
      class="mobile-list-item"
    >
      <van-icon :name="item.icon" class="mobile-list-item__icon" />
      <div class="mobile-list-item__content">
        <div class="mobile-list-item__title">{{ item.title }}</div>
        <div class="mobile-list-item__desc">{{ item.description }}</div>
      </div>
      <van-icon name="arrow" class="mobile-list-item__arrow" />
    </div>
  </div>
</template>
```

### 按钮组

```vue
<template>
  <div class="button-group">
    <van-button type="primary" size="small">主要按钮</van-button>
    <van-button type="default" size="small">默认按钮</van-button>
    <van-button plain type="primary" size="small">描边按钮</van-button>
  </div>
</template>

<style scoped>
.button-group {
  display: flex;
  gap: var(--mobile-space-md);
}
</style>
```

## 🎨 第三方 UI 库支持

### Element Plus Mobile

```typescript
import { applyElementPlusMobileTheme } from '@/theme/mobile'

// 应用 Element Plus Mobile 主题
applyElementPlusMobileTheme('purple')
```

### Ant Design Mobile

```typescript
import { applyAntDesignMobileTheme } from '@/theme/mobile'

// 应用 Ant Design Mobile 主题
applyAntDesignMobileTheme('gradient')
```

## 💾 主题持久化

主题选择会自动保存到 localStorage，下次访问时自动恢复。

```typescript
// 自定义持久化键名
const useThemeStorage = () => {
  return useLocalStorage<ThemeType>('custom-theme-key', 'light')
}
```

## 🎯 最佳实践

### 1. 组件内使用

```vue
<template>
  <div class="component" :style="componentStyles">
    <h3 :style="{ color: currentTheme.colors.text }">
      {{ title }}
    </h3>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useMobileTheme } from '@/theme/mobile'

const { currentTheme } = useMobileTheme()

const componentStyles = computed(() => ({
  backgroundColor: currentTheme.value.colors.cardBackground,
  borderRadius: currentTheme.value.radii.md
}))
</script>
```

### 2. 动态主题切换

```typescript
import { watch } from 'vue'
import { useMobileTheme } from '@/theme/mobile'

export default {
  setup() {
    const { currentThemeType, setTheme } = useMobileTheme()
    
    // 监听系统深色模式
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
    
    mediaQuery.addEventListener('change', (e) => {
      if (e.matches) {
        setTheme('dark')
      } else {
        setTheme('light')
      }
    })
    
    return { currentThemeType }
  }
}
```

### 3. 主题预设场景

```typescript
// 根据时间自动切换主题
const checkTimeTheme = () => {
  const hour = new Date().getHours()
  
  if (hour >= 18 || hour < 6) {
    setTheme('dark')  // 夜间使用深色主题
  } else {
    setTheme('light')
  }
}

// 启动时检查
checkTimeTheme()

// 每小时检查一次
setInterval(checkTimeTheme, 3600000)
```

## 📊 主题效果预览

### 主题预览缩略图
每个主题都有精美的预览缩略图，包含：
- 导航栏样式
- 内容区色块展示（主色、成功色、警告色、危险色）
- 底部标签栏
- 选中状态标识

### 主题效果说明
#### 浅色主题
- 适合光线充足的环境
- 标准的 UI 设计风格
- 最佳的可读性和对比度

#### 深色主题
- 适合夜间使用，保护眼睛
- 节省 OLED 屏幕电量
- 现代科技感

#### 商务蓝主题
- 适合企业正式场景
- 专业、稳重的视觉感受
- 适合金融、政府等行业

#### 渐变主题
- 适合创新、科技产品
- 年轻、活力的视觉感受
- 适合年轻用户群体

### 智能主题切换

#### 跟随系统深色模式
自动跟随系统的深色/浅色模式切换：

```vue
<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { useMobileTheme } from '@/theme/mobile'

const { setTheme } = useMobileTheme()

onMounted(() => {
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
  
  const handleChange = (e) => {
    setTheme(e.matches ? 'dark' : 'light')
  }
  
  mediaQuery.addEventListener('change', handleChange)
  handleChange({ matches: mediaQuery.matches })
})
</script>
```

#### 根据时间自动切换
根据时间自动在深色和浅色主题间切换：

```vue
<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useMobileTheme } from '@/theme/mobile'

const { setTheme } = useMobileTheme()
let intervalId = null

const checkTime = () => {
  const hour = new Date().getHours()
  if (hour >= 18 || hour < 6) {
    setTheme('dark')  // 夜间使用深色主题
  } else {
    setTheme('light')
  }
}

onMounted(() => {
  checkTime()
  intervalId = setInterval(checkTime, 60000) // 每分钟检查一次
})

onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId)
  }
})
</script>
```

## 🔧 自定义主题

### 创建自定义主题

```typescript
// themes.ts
export const myCustomTheme: ThemeConfig = {
  name: '我的自定义主题',
  type: 'custom',
  colors: {
    primary: '#FF6B6B',      // 自定义主色
    primaryLight: '#FFE3E3',
    primaryDark: '#EE5A5A',
    // ... 其他颜色
  },
  shadows: {
    sm: '0 1px 2px rgba(255, 107, 107, 0.1)',
    md: '0 2px 8px rgba(255, 107, 107, 0.2)',
    lg: '0 4px 16px rgba(255, 107, 107, 0.3)'
  },
  radii: {
    sm: '6px',
    md: '12px',
    lg: '16px',
    full: '9999px'
  }
}

// 注册到主题系统
themeRegistry['custom'] = myCustomTheme
```

### 注册自定义主题

```typescript
// index.ts
import { myCustomTheme } from './themes'

// 在 themeRegistry 中添加
export const themeRegistry = {
  light: lightTheme,
  dark: darkTheme,
  custom: myCustomTheme  // 新增
}
```

## 📱 响应式适配

### 安全区域支持

```css
.mobile-layout {
  padding-top: var(--mobile-safe-top);
  padding-bottom: var(--mobile-safe-bottom);
  padding-left: var(--mobile-safe-left);
  padding-right: var(--mobile-safe-right);
}
```

### 小屏幕优化

```css
@media (max-width: 320px) {
  :root {
    --mobile-font-size-lg: 14px;
    --mobile-space-lg: 12px;
  }
}
```

### 横屏适配

```css
@media (orientation: landscape) {
  .mobile-layout {
    min-height: 100vw;
  }
  
  .dashboard-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
```

## 🚀 性能优化

### 主题切换性能

```typescript
// 使用 CSS 变量而非 JavaScript 动态计算
// 浏览器会自动优化 CSS 变量重绘

const setTheme = (theme: ThemeType) => {
  // 只修改 CSS 变量，不触发组件重新渲染
  document.documentElement.style.setProperty('--mobile-primary', newColor)
}
```

### 懒加载主题

```typescript
// 只在需要时加载主题配置
const loadTheme = async (themeType: ThemeType) => {
  if (themeRegistry[themeType]) {
    return themeRegistry[themeType]
  }
  
  // 动态导入
  const module = await import(`./themes/${themeType}.ts`)
  return module.default
}
```

## 🧪 测试建议

### 主题切换测试

1. 所有主题下的 UI 元素可读性
2. 颜色对比度符合 WCAG 标准
3. 触摸目标尺寸符合要求（至少 44x44px）
4. 深色主题下的文字和图标清晰度
5. 主题切换的动画流畅度

### 响应式测试

1. iPhone SE (320px)
2. iPhone 12/13 (375px)
3. iPhone Plus/Max (414px)
4. iPad Mini (768px)
5. 横屏模式

## 📖 相关资源

- [Vant 官方文档](https://vant.pro/vant/#/zh-CN)
- [Element Plus Mobile](https://element-plus.org/)
- [Ant Design Mobile](https://mobile.ant.design/)
- [CSS 变量文档](https://developer.mozilla.org/zh-CN/docs/Web/CSS/Using_CSS_custom_properties)

## 🤝 贡献指南

欢迎提交新的主题方案！请确保：

1. 颜色对比度符合 WCAG 2.1 AA 标准
2. 所有组件在主题下都可正常显示
3. 提供完整的主题预览图
4. 更新 README 文档

## 📄 许可证

本主题方案遵循 DataEase 项目 GPL-3.0 许可证。
