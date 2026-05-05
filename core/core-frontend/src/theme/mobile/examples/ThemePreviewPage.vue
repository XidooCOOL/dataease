<template>
  <div class="theme-preview-page">
    <!-- 页面标题 -->
    <van-nav-bar title="主题设置" />
    
    <!-- 页面内容 -->
    <div class="theme-preview-page__content">
      <!-- 当前主题预览 -->
      <div class="theme-preview-page__current">
        <h3 class="theme-preview-page__section-title">当前主题</h3>
        <ThemePreview 
          :theme="currentThemeConfig" 
          :is-active="true"
        />
      </div>
      
      <!-- 所有主题 -->
      <div class="theme-preview-page__all">
        <h3 class="theme-preview-page__section-title">选择主题</h3>
        <div class="theme-preview-page__grid">
          <ThemePreview
            v-for="theme in themeList"
            :key="theme.type"
            :theme="theme"
            :is-active="currentThemeType === theme.type"
            @select="handleThemeSelect(theme.type)"
          />
        </div>
      </div>
      
      <!-- 智能切换设置 -->
      <div class="theme-preview-page__smart">
        <h3 class="theme-preview-page__section-title">智能切换</h3>
        <div class="theme-preview-page__options">
          <div class="theme-preview-page__option">
            <div class="theme-preview-page__option-label">
              <van-icon name="eye-o" />
              跟随系统深色模式
            </div>
            <van-switch 
              v-model="autoDarkMode" 
              size="small"
              @change="handleAutoDarkModeChange"
            />
          </div>
          
          <div class="theme-preview-page__option">
            <div class="theme-preview-page__option-label">
              <van-icon name="clock-o" />
              根据时间自动切换
            </div>
            <van-switch 
              v-model="autoTimeSwitch" 
              size="small"
              @change="handleAutoTimeSwitchChange"
            />
          </div>
        </div>
        
        <div class="theme-preview-page__time-info" v-if="autoTimeSwitch">
          <van-icon name="info-o" />
          <span>18:00 - 06:00 自动切换深色主题</span>
        </div>
      </div>
      
      <!-- 使用提示 -->
      <div class="theme-preview-page__tip">
        <h3 class="theme-preview-page__section-title">提示</h3>
        <div class="theme-preview-page__tip-content">
          <p>• 主题设置会保存到本地，下次访问自动生效</p>
          <p>• 可以在任意页面使用 <code>ThemeSwitcher</code> 组件快速切换主题</p>
          <p>• 深色模式适合夜间使用，可减少眼睛疲劳</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onUnmounted } from 'vue'
import { showToast } from 'vant'
import { useMobileTheme, getThemeConfig } from '@/theme/mobile/useMobileTheme'
import { themeList as allThemes, type ThemeType } from '@/theme/mobile/themes'
import ThemePreview from '@/theme/mobile/components/ThemePreview.vue'

const { currentThemeType, setTheme } = useMobileTheme()

const themeList = allThemes
const currentThemeConfig = getThemeConfig(currentThemeType.value)

const autoDarkMode = ref(false)
const autoTimeSwitch = ref(false)
let autoDarkModeMediaQuery: MediaQueryList | null = null
let autoTimeInterval: number | null = null

const handleThemeSelect = (themeType: ThemeType) => {
  if (themeType !== currentThemeType.value) {
    setTheme(themeType)
    showToast({
      message: `已切换至${getThemeConfig(themeType).name}`,
      position: 'bottom'
    })
  }
}

// 跟随系统深色模式
const handleAutoDarkModeChange = (enabled: boolean) => {
  if (enabled) {
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
        if (currentThemeType.value !== 'dark') {
          setTheme('dark')
          showToast({
            message: '已自动切换到深色主题',
            position: 'bottom'
          })
        }
      } else {
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
.theme-preview-page {
  min-height: 100vh;
  background-color: var(--mobile-background, #F5F6F7);
}

.theme-preview-page__content {
  padding: var(--mobile-space-lg, 16px);
  padding-bottom: calc(var(--mobile-space-xl, 24px) + var(--mobile-safe-bottom, 0px));
}

.theme-preview-page__current,
.theme-preview-page__all,
.theme-preview-page__smart,
.theme-preview-page__tip {
  background-color: var(--mobile-card-bg, #FFFFFF);
  border-radius: var(--mobile-radius-lg, 12px);
  padding: var(--mobile-space-lg, 16px);
  margin-bottom: var(--mobile-space-lg, 16px);
  box-shadow: var(--mobile-shadow-sm, 0 1px 2px rgba(0, 0, 0, 0.05));
}

.theme-preview-page__section-title {
  font-size: var(--mobile-font-size-lg, 16px);
  font-weight: 600;
  color: var(--mobile-text, #1F2329);
  margin: 0 0 var(--mobile-space-lg, 16px) 0;
}

.theme-preview-page__grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: var(--mobile-space-lg, 16px);
  justify-items: center;
}

.theme-preview-page__options {
  display: flex;
  flex-direction: column;
  gap: var(--mobile-space-md, 12px);
}

.theme-preview-page__option {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--mobile-space-sm, 8px) 0;
}

.theme-preview-page__option-label {
  display: flex;
  align-items: center;
  gap: var(--mobile-space-sm, 8px);
  font-size: var(--mobile-font-size-md, 14px);
  color: var(--mobile-text, #1F2329);
}

.theme-preview-page__time-info {
  display: flex;
  align-items: center;
  gap: var(--mobile-space-sm, 8px);
  margin-top: var(--mobile-space-md, 12px);
  padding: var(--mobile-space-md, 12px);
  background-color: var(--mobile-background, #F5F6F7);
  border-radius: var(--mobile-radius-md, 8px);
  font-size: var(--mobile-font-size-sm, 12px);
  color: var(--mobile-text-secondary, #646A73);
}

.theme-preview-page__tip-content {
  color: var(--mobile-text-secondary, #646A73);
  font-size: var(--mobile-font-size-sm, 12px);
  line-height: 1.8;
}

.theme-preview-page__tip-content p {
  margin: 0;
  padding-left: var(--mobile-space-xs, 4px);
}

.theme-preview-page__tip-content code {
  background-color: var(--mobile-primary-light, #EDF2FF);
  color: var(--mobile-primary, #3370FF);
  padding: 2px 6px;
  border-radius: 4px;
  font-size: var(--mobile-font-size-xs, 10px);
}

/* 响应式布局 */
@media (max-width: 480px) {
  .theme-preview-page__grid {
    grid-template-columns: repeat(3, 1fr);
    gap: var(--mobile-space-md, 12px);
  }
}

@media (max-width: 360px) {
  .theme-preview-page__grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
