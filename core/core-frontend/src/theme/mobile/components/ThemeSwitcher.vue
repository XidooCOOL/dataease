<template>
  <div class="theme-switcher">
    <van-popover v-model:show="showPicker" :actions="themeActions" @select="onSelect">
      <template #reference>
        <van-button size="small" icon="brush" type="primary" plain>
          切换主题
        </van-button>
      </template>
    </van-popover>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { showToast } from 'vant'
import { useMobileTheme, getThemeConfig } from '@/theme/mobile/useMobileTheme'
import type { ThemeType } from '@/theme/mobile/themes'

const { currentThemeType, setTheme } = useMobileTheme()
const showPicker = ref(false)

const themeActions = computed(() => {
  const themes = [
    { type: 'light', name: '浅色主题', color: '#3370FF' },
    { type: 'dark', name: '深色主题', color: '#1F2937' },
    { type: 'blue', name: '商务蓝', color: '#1E40AF' },
    { type: 'purple', name: '神秘紫', color: '#7C3AED' },
    { type: 'green', name: '清新绿', color: '#059669' },
    { type: 'orange', name: '活力橙', color: '#EA580C' },
    { type: 'pink', name: '少女粉', color: '#EC4899' },
    { type: 'gradient', name: '渐变', color: '#667EEA' }
  ]
  
  return themes.map(theme => ({
    ...theme,
    text: theme.name,
    disabled: theme.type === currentThemeType.value
  }))
})

const onSelect = (action: any) => {
  setTheme(action.type as ThemeType)
  showToast({
    message: `已切换至${action.name}`,
    position: 'bottom'
  })
  showPicker.value = false
}
</script>

<style scoped>
.theme-switcher {
  position: fixed;
  top: 60px;
  right: 16px;
  z-index: 1000;
}
</style>
