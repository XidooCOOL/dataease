/**
 * 移动端主题入口文件
 * 统一导出所有主题相关模块
 */

// 主题配置
export * from './themes'

// 主题 Hook
export * from './useMobileTheme'

// 第三方 UI 库主题配置
export * from './elementPlusMobile'
export * from './antDesignMobile'

// 组件
export { default as ThemeSwitcher } from './components/ThemeSwitcher.vue'

// 样式
import './styles/theme-variables.css'
