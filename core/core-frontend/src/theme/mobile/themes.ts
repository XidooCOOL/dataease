/**
 * DataEase 移动端主题配置
 * 基于 Vant UI 的多套主题方案
 */

// 主题类型定义
export type ThemeType = 
  | 'light'           // 浅色主题（默认）
  | 'dark'            // 深色主题
  | 'blue'            // 商务蓝主题
  | 'purple'          // 神秘紫主题
  | 'green'           // 清新绿主题
  | 'orange'          // 活力橙主题
  | 'pink'            // 少女粉主题
  | 'gradient'        // 渐变主题

export interface ThemeConfig {
  name: string
  type: ThemeType
  colors: {
    primary: string
    primaryLight: string
    primaryDark: string
    success: string
    warning: string
    danger: string
    info: string
    text: string
    textSecondary: string
    background: string
    backgroundLight: string
    border: string
    cardBackground: string
    navBarBackground: string
    tabBarBackground: string
  }
  shadows: {
    sm: string
    md: string
    lg: string
  }
  radii: {
    sm: string
    md: string
    lg: string
    full: string
  }
}

// 浅色主题配置
export const lightTheme: ThemeConfig = {
  name: '浅色主题',
  type: 'light',
  colors: {
    primary: '#3370FF',
    primaryLight: '#EDF2FF',
    primaryDark: '#1D4ED8',
    success: '#07C160',
    warning: '#FFB100',
    danger: '#EE4040',
    info: '#8A8A8A',
    text: '#1F2329',
    textSecondary: '#646A73',
    background: '#F5F6F7',
    backgroundLight: '#FFFFFF',
    border: '#E4E7ED',
    cardBackground: '#FFFFFF',
    navBarBackground: '#FFFFFF',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(0, 0, 0, 0.05)',
    md: '0 2px 8px rgba(0, 0, 0, 0.1)',
    lg: '0 4px 16px rgba(0, 0, 0, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 深色主题配置
export const darkTheme: ThemeConfig = {
  name: '深色主题',
  type: 'dark',
  colors: {
    primary: '#4F7DFE',
    primaryLight: '#2A3A5C',
    primaryDark: '#7B9EFF',
    success: '#34D399',
    warning: '#FBBF24',
    danger: '#F87171',
    info: '#6B7280',
    text: '#E5E7EB',
    textSecondary: '#9CA3AF',
    background: '#111827',
    backgroundLight: '#1F2937',
    border: '#374151',
    cardBackground: '#1F2937',
    navBarBackground: '#1F2937',
    tabBarBackground: '#1F2937'
  },
  shadows: {
    sm: '0 1px 2px rgba(0, 0, 0, 0.3)',
    md: '0 2px 8px rgba(0, 0, 0, 0.4)',
    lg: '0 4px 16px rgba(0, 0, 0, 0.5)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 商务蓝主题
export const blueTheme: ThemeConfig = {
  name: '商务蓝主题',
  type: 'blue',
  colors: {
    primary: '#1E40AF',
    primaryLight: '#DBEAFE',
    primaryDark: '#1E3A8A',
    success: '#059669',
    warning: '#D97706',
    danger: '#DC2626',
    info: '#64748B',
    text: '#1E293B',
    textSecondary: '#475569',
    background: '#F1F5F9',
    backgroundLight: '#FFFFFF',
    border: '#CBD5E1',
    cardBackground: '#FFFFFF',
    navBarBackground: '#1E40AF',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(30, 64, 175, 0.05)',
    md: '0 2px 8px rgba(30, 64, 175, 0.1)',
    lg: '0 4px 16px rgba(30, 64, 175, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 神秘紫主题
export const purpleTheme: ThemeConfig = {
  name: '神秘紫主题',
  type: 'purple',
  colors: {
    primary: '#7C3AED',
    primaryLight: '#EDE9FE',
    primaryDark: '#6D28D9',
    success: '#10B981',
    warning: '#F59E0B',
    danger: '#EF4444',
    info: '#6366F1',
    text: '#1F2937',
    textSecondary: '#6B7280',
    background: '#FAF5FF',
    backgroundLight: '#FFFFFF',
    border: '#DDD6FE',
    cardBackground: '#FFFFFF',
    navBarBackground: '#7C3AED',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(124, 58, 237, 0.05)',
    md: '0 2px 8px rgba(124, 58, 237, 0.1)',
    lg: '0 4px 16px rgba(124, 58, 237, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 清新绿主题
export const greenTheme: ThemeConfig = {
  name: '清新绿主题',
  type: 'green',
  colors: {
    primary: '#059669',
    primaryLight: '#D1FAE5',
    primaryDark: '#047857',
    success: '#10B981',
    warning: '#FBBF24',
    danger: '#EF4444',
    info: '#64748B',
    text: '#1F2937',
    textSecondary: '#6B7280',
    background: '#F0FDF4',
    backgroundLight: '#FFFFFF',
    border: '#A7F3D0',
    cardBackground: '#FFFFFF',
    navBarBackground: '#059669',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(5, 150, 105, 0.05)',
    md: '0 2px 8px rgba(5, 150, 105, 0.1)',
    lg: '0 4px 16px rgba(5, 150, 105, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 活力橙主题
export const orangeTheme: ThemeConfig = {
  name: '活力橙主题',
  type: 'orange',
  colors: {
    primary: '#EA580C',
    primaryLight: '#FFEDD5',
    primaryDark: '#C2410C',
    success: '#10B981',
    warning: '#FBBF24',
    danger: '#EF4444',
    info: '#64748B',
    text: '#1F2937',
    textSecondary: '#6B7280',
    background: '#FFF7ED',
    backgroundLight: '#FFFFFF',
    border: '#FDBA74',
    cardBackground: '#FFFFFF',
    navBarBackground: '#EA580C',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(234, 88, 12, 0.05)',
    md: '0 2px 8px rgba(234, 88, 12, 0.1)',
    lg: '0 4px 16px rgba(234, 88, 12, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 少女粉主题
export const pinkTheme: ThemeConfig = {
  name: '少女粉主题',
  type: 'pink',
  colors: {
    primary: '#EC4899',
    primaryLight: '#FCE7F3',
    primaryDark: '#DB2777',
    success: '#10B981',
    warning: '#FBBF24',
    danger: '#EF4444',
    info: '#64748B',
    text: '#1F2937',
    textSecondary: '#6B7280',
    background: '#FDF2F8',
    backgroundLight: '#FFFFFF',
    border: '#F9A8D4',
    cardBackground: '#FFFFFF',
    navBarBackground: '#EC4899',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(236, 72, 153, 0.05)',
    md: '0 2px 8px rgba(236, 72, 153, 0.1)',
    lg: '0 4px 16px rgba(236, 72, 153, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 渐变主题
export const gradientTheme: ThemeConfig = {
  name: '渐变主题',
  type: 'gradient',
  colors: {
    primary: '#667EEA',
    primaryLight: '#EDF2FF',
    primaryDark: '#5A67D8',
    success: '#10B981',
    warning: '#FBBF24',
    danger: '#EF4444',
    info: '#64748B',
    text: '#1F2937',
    textSecondary: '#6B7280',
    background: '#F7FAFC',
    backgroundLight: '#FFFFFF',
    border: '#E2E8F0',
    cardBackground: '#FFFFFF',
    navBarBackground: 'linear-gradient(135deg, #667EEA 0%, #764BA2 100%)',
    tabBarBackground: '#FFFFFF'
  },
  shadows: {
    sm: '0 1px 2px rgba(102, 126, 234, 0.05)',
    md: '0 2px 8px rgba(102, 126, 234, 0.1)',
    lg: '0 4px 16px rgba(102, 126, 234, 0.15)'
  },
  radii: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    full: '9999px'
  }
}

// 主题注册表
export const themeRegistry: Record<ThemeType, ThemeConfig> = {
  light: lightTheme,
  dark: darkTheme,
  blue: blueTheme,
  purple: purpleTheme,
  green: greenTheme,
  orange: orangeTheme,
  pink: pinkTheme,
  gradient: gradientTheme
}

// 获取主题配置
export const getThemeConfig = (type: ThemeType): ThemeConfig => {
  return themeRegistry[type] || lightTheme
}

// 导出所有主题列表
export const themeList = Object.values(themeRegistry)
