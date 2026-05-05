<template>
  <div class="theme-preview">
    <!-- 主题预览卡片 -->
    <div 
      class="theme-preview__card"
      :style="cardStyles"
      :class="{ 'theme-preview__card--active': isActive }"
      @click="handleClick"
    >
      <!-- 模拟导航栏 -->
      <div class="theme-preview__nav" :style="{ backgroundColor: navBgColor }">
        <div class="theme-preview__nav-title" :style="{ backgroundColor: textColor }"></div>
      </div>
      
      <!-- 模拟内容区 -->
      <div class="theme-preview__content" :style="{ backgroundColor: bgColor }">
        <div class="theme-preview__content-row">
          <div class="theme-preview__content-block" :style="{ backgroundColor: primaryColor }"></div>
          <div class="theme-preview__content-block" :style="{ backgroundColor: successColor }"></div>
        </div>
        <div class="theme-preview__content-row">
          <div class="theme-preview__content-block" :style="{ backgroundColor: warningColor }"></div>
          <div class="theme-preview__content-block" :style="{ backgroundColor: dangerColor }"></div>
        </div>
      </div>
      
      <!-- 模拟底部导航 -->
      <div class="theme-preview__tabbar" :style="{ backgroundColor: tabBgColor }">
        <div class="theme-preview__tab-item"></div>
        <div class="theme-preview__tab-item" :style="{ backgroundColor: primaryColor }"></div>
        <div class="theme-preview__tab-item"></div>
      </div>
      
      <!-- 选中标识 -->
      <div v-if="isActive" class="theme-preview__checkmark">
        <van-icon name="success" size="20" color="#FFFFFF" />
      </div>
    </div>
    
    <!-- 主题名称 -->
    <div class="theme-preview__name">
      {{ name }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { ThemeConfig } from '@/theme/mobile/themes'

interface Props {
  theme: ThemeConfig
  isActive: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<{
  select: []
}>()

// 计算主题预览颜色
const primaryColor = computed(() => props.theme.colors.primary)
const successColor = computed(() => props.theme.colors.success)
const warningColor = computed(() => props.theme.colors.warning)
const dangerColor = computed(() => props.theme.colors.danger)
const bgColor = computed(() => props.theme.colors.background)
const navBgColor = computed(() => props.theme.colors.navBarBackground)
const tabBgColor = computed(() => props.theme.colors.tabBarBackground)
const textColor = computed(() => props.theme.colors.text)
const cardBgColor = computed(() => props.theme.colors.cardBackground)

// 卡片样式
const cardStyles = computed(() => ({
  backgroundColor: cardBgColor.value,
  borderColor: props.isActive ? primaryColor.value : props.theme.colors.border
}))

const handleClick = () => {
  emit('select')
}
</script>

<style scoped>
.theme-preview {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.theme-preview__card {
  position: relative;
  width: 120px;
  height: 160px;
  border-radius: 12px;
  border: 2px solid transparent;
  overflow: hidden;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.theme-preview__card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.theme-preview__card--active {
  transform: scale(1.05);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

.theme-preview__checkmark {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 28px;
  height: 28px;
  background-color: var(--mobile-primary, #3370FF);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.theme-preview__nav {
  height: 28px;
  display: flex;
  align-items: center;
  padding: 0 12px;
  background: linear-gradient(135deg, var(--mobile-primary, #3370FF) 0%, var(--mobile-primary-dark, #1D4ED8) 100%);
}

.theme-preview__nav-title {
  width: 40px;
  height: 8px;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.8);
}

.theme-preview__content {
  flex: 1;
  padding: 8px;
  display: flex;
  flex-direction: column;
  gap: 6px;
  background-color: var(--mobile-background, #F5F6F7);
}

.theme-preview__content-row {
  display: flex;
  gap: 6px;
  flex: 1;
}

.theme-preview__content-block {
  flex: 1;
  border-radius: 6px;
  opacity: 0.8;
}

.theme-preview__tabbar {
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 0 16px;
  background-color: var(--mobile-tab-bg, #FFFFFF);
  border-top: 1px solid var(--mobile-border, #E4E7ED);
}

.theme-preview__tab-item {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: var(--mobile-border, #E4E7ED);
  opacity: 0.5;
}

.theme-preview__name {
  margin-top: 8px;
  font-size: 12px;
  color: var(--mobile-text-secondary, #646A73);
  text-align: center;
}
</style>
