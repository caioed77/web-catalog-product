<template>
  <div class="bg-black shadow-md px-5 py-5">
    <div class="flex gap-2 items-center">
      <nav class="font-bold text-2xl text-white">
        <PhShoppingBagOpen :size="40" class="text-white" />
      </nav>
      <h1 class="text-white text-lg font-semibold">Catalogo</h1>
    </div>
  </div>
</template>

<script setup lang="ts">
import { PhShoppingBagOpen } from "@phosphor-icons/vue";
</script>
