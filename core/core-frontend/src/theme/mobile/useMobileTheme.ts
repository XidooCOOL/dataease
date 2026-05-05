/**
 * 移动端主题管理 Hook
 * 提供主题切换、应用和持久化功能
 */

import { ref, computed, watch } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import { themeRegistry, getThemeConfig, type ThemeType, type ThemeConfig } from './themes'

// 持久化存储当前主题
const useThemeStorage = () => {
  return useLocalStorage<ThemeType>('dataease-mobile-theme', 'light')
}

// 当前主题类型
const currentThemeType = ref<ThemeType>('light')

// 当前主题配置
const currentTheme = computed<ThemeConfig>(() => {
  return getThemeConfig(currentThemeType.value)
})

// 初始化主题
export const useMobileTheme = () => {
  const storedTheme = useThemeStorage()
  
  // 从存储中恢复主题
  if (storedTheme.value && themeRegistry[storedTheme.value]) {
    currentThemeType.value = storedTheme.value
  }
  
  // 监听主题变化，持久化存储
  watch(currentThemeType, (newTheme) => {
    storedTheme.value = newTheme
    applyTheme(newTheme)
  }, { immediate: true })
  
  // 切换主题
  const setTheme = (theme: ThemeType) => {
    if (themeRegistry[theme]) {
      currentThemeType.value = theme
    }
  }
  
  // 获取所有可用主题
  const getThemeList = () => {
    return Object.entries(themeRegistry).map(([key, config]) => ({
      type: key as ThemeType,
      name: config.name,
      preview: config.colors.primary
    }))
  }
  
  return {
    currentThemeType,
    currentTheme,
    setTheme,
    getThemeList
  }
}

// 应用主题到 DOM
export const applyTheme = (themeType: ThemeType) => {
  const theme = getThemeConfig(themeType)
  const root = document.documentElement
  
  // 应用颜色变量
  root.style.setProperty('--mobile-primary', theme.colors.primary)
  root.style.setProperty('--mobile-primary-light', theme.colors.primaryLight)
  root.style.setProperty('--mobile-primary-dark', theme.colors.primaryDark)
  root.style.setProperty('--mobile-success', theme.colors.success)
  root.style.setProperty('--mobile-warning', theme.colors.warning)
  root.style.setProperty('--mobile-danger', theme.colors.danger)
  root.style.setProperty('--mobile-info', theme.colors.info)
  root.style.setProperty('--mobile-text', theme.colors.text)
  root.style.setProperty('--mobile-text-secondary', theme.colors.textSecondary)
  root.style.setProperty('--mobile-background', theme.colors.background)
  root.style.setProperty('--mobile-background-light', theme.colors.backgroundLight)
  root.style.setProperty('--mobile-border', theme.colors.border)
  root.style.setProperty('--mobile-card-bg', theme.colors.cardBackground)
  root.style.setProperty('--mobile-nav-bg', theme.colors.navBarBackground)
  root.style.setProperty('--mobile-tab-bg', theme.colors.tabBarBackground)
  
  // 应用阴影
  root.style.setProperty('--mobile-shadow-sm', theme.shadows.sm)
  root.style.setProperty('--mobile-shadow-md', theme.shadows.md)
  root.style.setProperty('--mobile-shadow-lg', theme.shadows.lg)
  
  // 应用圆角
  root.style.setProperty('--mobile-radius-sm', theme.radii.sm)
  root.style.setProperty('--mobile-radius-md', theme.radii.md)
  root.style.setProperty('--mobile-radius-lg', theme.radii.lg)
  root.style.setProperty('--mobile-radius-full', theme.radii.full)
  
  // 更新 Vant 主题变量
  root.style.setProperty('--van-primary-color', theme.colors.primary)
  
  // 应用到 body
  document.body.style.backgroundColor = theme.colors.background
  document.body.style.color = theme.colors.text
  
  // 添加主题 class
  document.body.classList.remove(...Object.keys(themeRegistry))
  document.body.classList.add(`mobile-theme-${themeType}`)
}

// 响应式主题变量（供 CSS 使用）
export const mobileThemeVars = computed(() => {
  const theme = currentTheme.value
  return {
    primary: theme.colors.primary,
    primaryLight: theme.colors.primaryLight,
    primaryDark: theme.colors.primaryDark,
    success: theme.colors.success,
    warning: theme.colors.warning,
    danger: theme.colors.danger,
    info: theme.colors.info,
    text: theme.colors.text,
    textSecondary: theme.colors.textSecondary,
    background: theme.colors.background,
    backgroundLight: theme.colors.backgroundLight,
    border: theme.colors.border,
    cardBg: theme.colors.cardBackground,
    navBg: theme.colors.navBarBackground,
    tabBg: theme.colors.tabBarBackground,
    shadowSm: theme.shadows.sm,
    shadowMd: theme.shadows.md,
    shadowLg: theme.shadows.lg,
    radiusSm: theme.radii.sm,
    radiusMd: theme.radii.md,
    radiusLg: theme.radii.lg,
    radiusFull: theme.radii.full
  }
})
