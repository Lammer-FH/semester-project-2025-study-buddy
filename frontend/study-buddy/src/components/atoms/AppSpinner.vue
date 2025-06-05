<template>
  <div class="spinner-container">
    <ion-spinner name="crescent"></ion-spinner>
    <span v-if="message">{{ message }}</span>
  </div>
</template>

<script setup lang="ts">
import { IonSpinner } from "@ionic/vue";
defineProps<{
  message?: string;
}>();
</script>

<style scoped>
.spinner-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 16px;
}
</style>
