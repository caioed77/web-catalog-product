<template>
  <div class="flex flex-col items-center mt-5">
    <Paginacao />
    <div class="flex flex-wrap items-center justify-center gap-6">
      <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-10 mt-10">
        <div
          v-for="product in Produtos?.content"
          :key="product?.id"
          class="p-2 bg-amber-50 border hover:border-black rounded shadow-md w-80"
        >
          <img
            :src="product?.imagem"
            :alt="product?.imagem"
            class="max-w-full h-auto mb-4 object-contain object-center"
          />
          <p class="text-gray-700 font-bold">{{ product?.descricao }}</p>
          <p class="text-gray-700">{{ product?.precoUnitario }}</p>
          <p class="text-gray-700">R$ {{ product?.precoUnitario }}</p>
        </div>
      </div>
    </div>
    <DialogProduto :open="false" />
  </div>
</template>

<script setup lang="ts">
import DialogProduto from "../components/DialogProduto.vue";
import Paginacao from "../components/Paginacao.vue";
import { IProdutos } from "../types/ProdutosType";
import { IPaginacao } from "../types/PaginacaoType";
import { ref, onMounted } from "vue";
import { retornarProduto } from "../services/ProdutosService";
const Produtos = ref<IPaginacao<IProdutos | undefined>>();

onMounted(async () => {
  Produtos.value = await retornarProduto();  
});
</script>
