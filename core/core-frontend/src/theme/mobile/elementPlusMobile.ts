/**
 * Element Plus Mobile 主题配置
 * 基于 Vue 3 + Varlet 的移动端 UI 库主题
 */

import type { ThemeType } from '@/theme/mobile/themes'
import { themeRegistry } from '@/theme/mobile/themes'

export interface ElementPlusMobileTheme {
  '--el-color-primary': string
  '--el-color-primary-light-3': string
  '--el-color-primary-light-5': string
  '--el-color-primary-light-7': string
  '--el-color-primary-light-8': string
  '--el-color-primary-light-9': string
  '--el-color-primary-dark-2': string
  '--el-bg-color': string
  '--el-bg-color-overlay': string
  '--el-text-color-primary': string
  '--el-text-color-regular': string
  '--el-text-color-secondary': string
  '--el-text-color-placeholder': string
  '--el-border-color': string
  '--el-border-color-light': string
  '--el-border-color-lighter': string
  '--el-fill-color': string
  '--el-fill-color-light': string
  '--el-fill-color-lighter': string
  '--el-box-shadow': string
  '--el-border-radius-base': string
}

export const generateElementPlusMobileTheme = (type: ThemeType): ElementPlusMobileTheme => {
  const theme = themeRegistry[type]
  
  return {
    '--el-color-primary': theme.colors.primary,
    '--el-color-primary-light-3': theme.colors.primaryLight,
    '--el-color-primary-light-5': theme.colors.primaryLight,
    '--el-color-primary-light-7': theme.colors.primaryLight,
    '--el-color-primary-light-8': theme.colors.backgroundLight,
    '--el-color-primary-light-9': theme.colors.backgroundLight,
    '--el-color-primary-dark-2': theme.colors.primaryDark,
    '--el-bg-color': theme.colors.background,
    '--el-bg-color-overlay': theme.colors.cardBackground,
    '--el-text-color-primary': theme.colors.text,
    '--el-text-color-regular': theme.colors.text,
    '--el-text-color-secondary': theme.colors.textSecondary,
    '--el-text-color-placeholder': theme.colors.textSecondary,
    '--el-border-color': theme.colors.border,
    '--el-border-color-light': theme.colors.border,
    '--el-border-color-lighter': theme.colors.border,
    '--el-fill-color': theme.colors.backgroundLight,
    '--el-fill-color-light': theme.colors.backgroundLight,
    '--el-fill-color-lighter': theme.colors.background,
    '--el-box-shadow': theme.shadows.md,
    '--el-border-radius-base': theme.radii.md
  }
}

// 应用 Element Plus Mobile 主题
export const applyElementPlusMobileTheme = (type: ThemeType) => {
  const vars = generateElementPlusMobileTheme(type)
  const root = document.documentElement
  
  Object.entries(vars).forEach(([key, value]) => {
    root.style.setProperty(key, value)
  })
}
