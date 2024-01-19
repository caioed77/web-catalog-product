<template>
  <div class="dsmovie-pagination-container">
    <div class="dsmovie-pagination-box">
      <button class="dsmovie-pagination-button">
        <Arrow />
      </button>
      <p>{`${page.number + 1} de ${page.totalPages}`}</p>
      <button class="dsmovie-pagination-button">
        <Arrow class="dsmovie-flip-horizontal" />
      </button>
    </div>
  </div>
</template>

<script setup lang="ts"></script>
