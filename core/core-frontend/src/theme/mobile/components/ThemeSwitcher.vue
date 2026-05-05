<template>
  <div class="theme-switcher">
    <!-- 触发按钮 -->
    <van-button 
      size="small" 
      icon="brush" 
      type="primary" 
      plain
      @click="showThemePicker = true"
      class="theme-switcher__button"
    >
      切换主题
    </van-button>
    
    <!-- 主题选择弹窗 -->
    <van-popup 
      v-model:show="showThemePicker"
      position="bottom"
      round
      :style="{ height: '60%' }"
      class="theme-switcher__popup"
    >
      <ThemePicker 
        @close="showThemePicker = false"
        @change="handleThemeChange"
      />
    </van-popup>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ThemePicker from './ThemePicker.vue'
import type { ThemeType } from '@/theme/mobile/themes'

const showThemePicker = ref(false)

const handleThemeChange = (theme: ThemeType) => {
  // 主题已在 ThemePicker 中切换，这里可以处理额外逻辑
}
</script>

<style scoped>
.theme-switcher {
  position: fixed;
  top: 60px;
  right: 16px;
  z-index: 1000;
}

.theme-switcher__button {
  box-shadow: var(--mobile-shadow-md, 0 2px 8px rgba(0, 0, 0, 0.1));
}

.theme-switcher__popup {
  --van-popup-round-radius: 16px 16px 0 0;
}

:deep(.theme-switcher__popup .van-popup__close-icon) {
  top: 12px;
  right: 12px;
}
</style>
