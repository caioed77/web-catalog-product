import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface ITokenStoreState {
  exibir: boolean
}

export const useTokenStore = defineStore('useTokenStore', () => {
  const state = ref<ITokenStoreState>({ exibir: false })

  function exibeMensagem() {
    state.value = {
      exibir: true,
    }
  }

  function ocultarMensagem() {
    state.value = {
      exibir: false,
    }
  }

  return { exibeMensagem, ocultarMensagem, state }
})