<template>
  <NavBar />
  <div class="min-h-screen flex flex-col h-screen overflow-hidden">
    <!-- main container -->
    <div class="flex-1 flex flex-row overflow-hidden">
      <main class="flex-1 bg-white overflow-auto">
        <div id="Dashboard" class="bg-white m-0">
          <RouterView />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import NavBar from "../components/NavBar.vue";
</script>
