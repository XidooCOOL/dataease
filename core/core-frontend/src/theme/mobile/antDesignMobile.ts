/**
 * Ant Design Mobile 主题配置
 * 蚂蚁金服移动端 UI 库主题
 */

import type { ThemeType } from '@/theme/mobile/themes'
import { themeRegistry } from '@/theme/mobile/themes'

export interface AntDesignMobileTheme {
  '--adm-color-primary': string
  '--adm-color-success': string
  '--adm-color-warning': string
  '--adm-color-danger': string
  '--adm-color-info': string
  '--adm-color-text': string
  '--adm-color-text-secondary': string
  '--adm-color-disabled': string
  '--adm-color-border': string
  '--adm-color-fill': string
  '--adm-font-size-1': string
  '--adm-font-size-2': string
  '--adm-font-size-3': string
  '--adm-font-size-4': string
  '--adm-font-size-5': string
  '--adm-border-radius': string
  '--adm-box-shadow': string
}

export const generateAntDesignMobileTheme = (type: ThemeType): AntDesignMobileTheme => {
  const theme = themeRegistry[type]
  
  return {
    '--adm-color-primary': theme.colors.primary,
    '--adm-color-success': theme.colors.success,
    '--adm-color-warning': theme.colors.warning,
    '--adm-color-danger': theme.colors.danger,
    '--adm-color-info': theme.colors.info,
    '--adm-color-text': theme.colors.text,
    '--adm-color-text-secondary': theme.colors.textSecondary,
    '--adm-color-disabled': theme.colors.textSecondary,
    '--adm-color-border': theme.colors.border,
    '--adm-color-fill': theme.colors.backgroundLight,
    '--adm-font-size-1': '10px',
    '--adm-font-size-2': '12px',
    '--adm-font-size-3': '14px',
    '--adm-font-size-4': '16px',
    '--adm-font-size-5': '18px',
    '--adm-border-radius': theme.radii.md,
    '--adm-box-shadow': theme.shadows.md
  }
}

// 应用 Ant Design Mobile 主题
export const applyAntDesignMobileTheme = (type: ThemeType) => {
  const vars = generateAntDesignMobileTheme(type)
  const root = document.documentElement
  
  Object.entries(vars).forEach(([key, value]) => {
    root.style.setProperty(key, value)
  })
}
