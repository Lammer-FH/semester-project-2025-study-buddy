<template>
  <base-layout page-title="Info">
    <h2>Study Buddy</h2>
    <h5>Your personal study assistent</h5>
    <p>
      Studdy buddy helps millions of students manage university courses, track
      assignments, organize tasks, and meet deadlines. The app lets you create
      and organize assignments per course and track completion.
    </p>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseLayout from "@/components/template/BaseLayout.vue";
export default defineComponent({
  name: "InfoPage",
  components: {
    BaseLayout,
  },
});
</script>
