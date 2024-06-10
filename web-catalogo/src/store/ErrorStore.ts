import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface IAppError {
  exibir: boolean
  mensagemErro: string
  detalhesErro?: string
}

export const useErroStore = defineStore('useAppStore', () => {
  const stateErro = ref<IAppError>({ mensagemErro: '', exibir: false })

  function addErro(mensagemErro: string, detalhesErro?: string) {
    stateErro.value = {
      mensagemErro,
      detalhesErro,
      exibir: mensagemErro !== '' && mensagemErro !== undefined,
    }
  }

  function ocultarError() {
    stateErro.value.exibir = false

    setTimeout(() => {
      stateErro.value = {
        ...stateErro.value,
        mensagemErro: '',
        detalhesErro: '',
      }
    }, 300)
  }
  return { stateErro, addErro, ocultarError }
})