import { ref } from 'vue'
import { defineStore } from 'pinia'

export interface ILoadingState {
  loading: boolean
}

export const useLoadingStore = defineStore('useLoadingStore', () => {
  const stateLoading = ref<ILoadingState>({ loading: false })

  function showLoading() {
    stateLoading.value.loading = true
  }

  function hideLoading() {
    stateLoading.value.loading = false
  }

  return { stateLoading, showLoading, hideLoading }
})