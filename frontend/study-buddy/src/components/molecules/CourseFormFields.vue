<!-- <template>
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
</script> -->

<!-- components/CourseFormFields.vue -->
<template>
  <form>
    <ion-item :class="{ 'ion-invalid': showTitleError }">
      <ion-label position="stacked">Title *</ion-label>
      <ion-input v-model="localCourse.title" type="text" required />
    </ion-item>
    <ion-note
      v-if="showTitleError"
      color="danger"
      style="padding-left: 16px; padding-top: 4px"
    >
      Title is required
    </ion-note>

    <ion-item>
      <ion-label position="stacked">Description</ion-label>
      <ion-textarea v-model="localCourse.description" :rows="5" />
    </ion-item>
  </form>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonItem, IonLabel, IonInput, IonTextarea, IonNote } from "@ionic/vue";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseFormFields",
  components: {
    IonItem,
    IonLabel,
    IonInput,
    IonTextarea,
    IonNote,
  },
  props: {
    course: {
      type: Object as () => Course,
      required: true,
    },
    validateFields: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:course", "validation-change"],
  data() {
    return {
      localCourse: { ...this.course },
    };
  },
  computed: {
    showTitleError(): boolean {
      return (
        this.validateFields &&
        (!this.localCourse.title || this.localCourse.title.trim() === "")
      );
    },
    isValid(): boolean {
      return !!(this.localCourse.title && this.localCourse.title.trim());
    },
  },
  watch: {
    localCourse: {
      handler(newValue) {
        this.$emit("update:course", { ...newValue });
        this.$emit("validation-change", this.isValid);
      },
      deep: true,
    },
    isValid(newValue) {
      this.$emit("validation-change", newValue);
    },
  },
});
</script>
