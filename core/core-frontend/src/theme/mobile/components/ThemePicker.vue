<template>
  <div class="theme-picker">
    <!-- 标题栏 -->
    <div class="theme-picker__header">
      <h3 class="theme-picker__title">选择主题</h3>
      <van-button 
        size="small" 
        type="default" 
        plain 
        @click="handleClose"
        class="theme-picker__close"
      >
        关闭
      </van-button>
    </div>
    
    <!-- 主题网格 -->
    <div class="theme-picker__grid">
      <ThemePreview
        v-for="theme in themeList"
        :key="theme.type"
        :theme="theme"
        :is-active="currentThemeType === theme.type"
        @select="handleThemeSelect(theme.type)"
        class="theme-picker__item"
      />
    </div>
    
    <!-- 主题设置 -->
    <div class="theme-picker__settings">
      <div class="theme-picker__setting-item">
        <span class="theme-picker__setting-label">跟随系统深色模式</span>
        <van-switch 
          v-model="autoDarkMode" 
          size="small"
          @change="handleAutoDarkModeChange"
        />
      </div>
      <div class="theme-picker__setting-item">
        <span class="theme-picker__setting-label">根据时间自动切换</span>
        <van-switch 
          v-model="autoTimeSwitch" 
          size="small"
          @change="handleAutoTimeSwitchChange"
        />
      </div>
    </div>
    
    <!-- 说明文字 -->
    <div class="theme-picker__tip">
      <van-icon name="info-o" />
      <span>主题设置会保存到本地，下次访问自动生效</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { showToast } from 'vant'
import { useMobileTheme, getThemeConfig } from '@/theme/mobile/useMobileTheme'
import { themeList as allThemes, type ThemeType } from '@/theme/mobile/themes'
import ThemePreview from './ThemePreview.vue'

const props = defineProps<{
  showClose?: boolean
}>()

const emit = defineEmits<{
  close: []
  change: [theme: ThemeType]
}>()

const { currentThemeType, setTheme } = useMobileTheme()

const autoDarkMode = ref(false)
const autoTimeSwitch = ref(false)
let autoDarkModeMediaQuery: MediaQueryList | null = null
let autoTimeInterval: number | null = null

// 过滤并获取所有主题
const themeList = allThemes

const handleThemeSelect = (themeType: ThemeType) => {
  if (themeType !== currentThemeType.value) {
    setTheme(themeType)
    showToast({
      message: `已切换至${getThemeConfig(themeType).name}`,
      position: 'bottom'
    })
    emit('change', themeType)
  }
}

const handleClose = () => {
  emit('close')
}

// 跟随系统深色模式
const handleAutoDarkModeChange = (enabled: boolean) => {
  if (enabled) {
    // 检查系统深色模式
    autoDarkModeMediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
    
    const checkDarkMode = (e?: MediaQueryListEvent) => {
      if (autoDarkMode.value) {
        setTheme(e?.matches ? 'dark' : 'light')
      }
    }
    
    checkDarkMode()
    autoDarkModeMediaQuery.addEventListener('change', checkDarkMode)
  } else {
    if (autoDarkModeMediaQuery) {
      autoDarkModeMediaQuery.removeEventListener('change', () => {})
    }
  }
}

// 根据时间自动切换
const handleAutoTimeSwitchChange = (enabled: boolean) => {
  if (enabled) {
    const checkTime = () => {
      if (!autoTimeSwitch.value) return
      
      const hour = new Date().getHours()
      
      if (hour >= 18 || hour < 6) {
        // 夜间（18:00 - 06:00）使用深色主题
        if (currentThemeType.value !== 'dark') {
          setTheme('dark')
          showToast({
            message: '已自动切换到深色主题',
            position: 'bottom'
          })
        }
      } else {
        // 白天使用浅色主题
        if (currentThemeType.value !== 'light') {
          setTheme('light')
          showToast({
            message: '已自动切换到浅色主题',
            position: 'bottom'
          })
        }
      }
    }
    
    checkTime()
    autoTimeInterval = window.setInterval(checkTime, 60000) // 每分钟检查一次
  } else {
    if (autoTimeInterval) {
      clearInterval(autoTimeInterval)
      autoTimeInterval = null
    }
  }
}

onUnmounted(() => {
  if (autoDarkModeMediaQuery) {
    autoDarkModeMediaQuery.removeEventListener('change', () => {})
  }
  if (autoTimeInterval) {
    clearInterval(autoTimeInterval)
  }
})
</script>

<style scoped>
.theme-picker {
  background-color: var(--mobile-card-bg, #FFFFFF);
  border-radius: var(--mobile-radius-lg, 12px);
  padding: var(--mobile-space-lg, 16px);
  max-width: 500px;
  margin: 0 auto;
}

.theme-picker__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--mobile-space-lg, 16px);
}

.theme-picker__title {
  font-size: var(--mobile-font-size-lg, 16px);
  font-weight: 600;
  color: var(--mobile-text, #1F2329);
  margin: 0;
}

.theme-picker__grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: var(--mobile-space-lg, 16px);
  margin-bottom: var(--mobile-space-xl, 24px);
}

.theme-picker__item {
  display: flex;
  justify-content: center;
}

.theme-picker__settings {
  border-top: 1px solid var(--mobile-border, #E4E7ED);
  padding-top: var(--mobile-space-lg, 16px);
  margin-bottom: var(--mobile-space-lg, 16px);
}

.theme-picker__setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--mobile-space-md, 12px) 0;
}

.theme-picker__setting-label {
  font-size: var(--mobile-font-size-md, 14px);
  color: var(--mobile-text, #1F2329);
}

.theme-picker__tip {
  display: flex;
  align-items: center;
  gap: var(--mobile-space-sm, 8px);
  padding: var(--mobile-space-md, 12px);
  background-color: var(--mobile-background, #F5F6F7);
  border-radius: var(--mobile-radius-md, 8px);
  color: var(--mobile-text-secondary, #646A73);
  font-size: var(--mobile-font-size-sm, 12px);
}

.theme-picker__tip .van-icon {
  color: var(--mobile-info, #8A8A8A);
}

/* 响应式布局 */
@media (max-width: 480px) {
  .theme-picker__grid {
    grid-template-columns: repeat(3, 1fr);
    gap: var(--mobile-space-md, 12px);
  }
}

@media (max-width: 360px) {
  .theme-picker__grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
