<template>
  <div class="flex flex-col items-center mt-5">
    <Paginacao />
    <div class="flex flex-wrap items-center justify-center gap-6">
      <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-10 mt-10">
        <div
          v-for="product in products"
          :key="product.id"
          class="p-2 bg-amber-50 border hover:border-black rounded shadow-md w-80"
        >
          <img
            :src="product.image"
            :alt="product.name"
            class="max-w-full h-auto mb-4 object-contain object-center"
          />
          <p class="text-gray-700 font-bold">{{ product.description }}</p>
          <p class="text-gray-700">{{ product.tamanho }}</p>
          <p class="text-gray-700">R$ {{ product.value.toFixed(2) }}</p>
        </div>
      </div>
    </div>
    <DialogProduto :open="true" />
  </div>
</template>

<script setup lang="ts">
import DialogProduto from "../components/DialogProduto.vue";
import Paginacao from "../components/Paginacao.vue";
const products = [
  {
    image:
      "https://t-static.dafiti.com.br/RfZRx0pDUbrHz9r24i_eKoeljIU=/fit-in/430x623/static.dafiti.com.br/p/gb-kit-3-pares-de-sapat%c3%aanis-masculino-cal%c3%a7ados-1813-9473899-1-zoom.jpg",
    name: "string",
    id: 1,
    description: "Nike air max off-white",
    value: 20.0,
    tamanho: '34 ao 40'
  },
  {
    image:
      "https://t-static.dafiti.com.br/RfZRx0pDUbrHz9r24i_eKoeljIU=/fit-in/430x623/static.dafiti.com.br/p/gb-kit-3-pares-de-sapat%c3%aanis-masculino-cal%c3%a7ados-1813-9473899-1-zoom.jpg",
    name: "string",
    id: 2,
    description: "Tenis teste",
    value: 20.0,
    tamanho: '34 ao 40'
  },
  {
    image:
      "https://t-static.dafiti.com.br/RfZRx0pDUbrHz9r24i_eKoeljIU=/fit-in/430x623/static.dafiti.com.br/p/gb-kit-3-pares-de-sapat%c3%aanis-masculino-cal%c3%a7ados-1813-9473899-1-zoom.jpg",
    name: "string",
    id: 3,
    description: "Tenis teste",
    value: 20.0,
    tamanho: '34 ao 40'
  },
  {
    image:
      "https://t-static.dafiti.com.br/RfZRx0pDUbrHz9r24i_eKoeljIU=/fit-in/430x623/static.dafiti.com.br/p/gb-kit-3-pares-de-sapat%c3%aanis-masculino-cal%c3%a7ados-1813-9473899-1-zoom.jpg",
    name: "string",
    id: 3,
    description: "Tenis teste",
    value: 20.0,
    tamanho: '34 ao 40'
  },
];
</script>
