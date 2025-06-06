<!-- components/CourseFormFields.vue -->
<template>
  <ion-item>
    <ion-label position="stacked">Title</ion-label>
    <ion-input v-model="localCourse.title" type="text" required />
  </ion-item>

  <ion-item>
    <ion-label position="stacked">Description</ion-label>
    <ion-textarea v-model="localCourse.description" :rows="5" />
  </ion-item>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonItem, IonLabel, IonInput, IonTextarea } from "@ionic/vue";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseFormFields",
  components: {
    IonItem,
    IonLabel,
    IonInput,
    IonTextarea,
  },
  props: {
    course: {
      type: Object as () => Course,
      required: true,
    },
  },
  emits: ["update:course"],
  data() {
    return {
      localCourse: { ...this.course },
    };
  },
  watch: {
    localCourse: {
      handler(newValue) {
        this.$emit("update:course", { ...newValue });
      },
      deep: true,
    },
  },
});
</script>
