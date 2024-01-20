<template>
  <div class="py-4 flex justify-center items-center">
    <div class="w-48 flex justify-between items-center">
      <button class="text-2xl border hover:border-black p-2">
        <PhCaretLeft class="filter brightness-0 saturate-100 invert-26 sepia-19 saturate-7395 hue-rotate-234 brightness-89 contrast-92" />
      </button>
      <p class="text-primary text-sm">{{ `${page.number + 1} de ${page.totalPages}` }}</p>
      <button class="text-2xl border hover:border-black p-2">
        <PhCaretRight class="filter brightness-0 saturate-100 invert-26 sepia-19 saturate-7395 hue-rotate-234 brightness-89 contrast-92" />
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { PhCaretLeft, PhCaretRight } from '@phosphor-icons/vue';
import { ref } from 'vue';
const page = ref({number: 1, totalPages: 1})
</script>
