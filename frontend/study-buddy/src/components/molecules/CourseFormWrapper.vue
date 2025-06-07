<!-- <template>
  <div v-if="localCourse">
    <course-form-fields :course="localCourse" @update:course="updateLocal" />
    <ion-button expand="block" @click="submit">
      {{ buttonLabel }}
    </ion-button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormFields from "@/components/molecules/CourseFormFields.vue";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseFormWrapper",
  components: { CourseFormFields },
  props: {
    initialCourse: {
      type: Object as () => Partial<Course>,
      required: true,
    },
    buttonLabel: { type: String, default: "Submit" },
  },
  data() {
    return { localCourse: { ...this.initialCourse } as Course };
  },
  methods: {
    updateLocal(updated: Course) {
      this.localCourse = updated;
    },
    submit() {
      this.$emit("submit", { ...this.localCourse });
    },
  },
});
</script> -->

<template>
  <div v-if="localCourse">
    <course-form-fields
      :course="localCourse"
      :validate-fields="hasTriedSubmit"
      @update:course="updateLocal"
      @validation-change="handleValidationChange"
    />
    <ion-button expand="block" @click="submit">
      {{ buttonLabel }}
    </ion-button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormFields from "@/components/molecules/CourseFormFields.vue";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseFormWrapper",
  components: { CourseFormFields },
  props: {
    initialCourse: {
      type: Object as () => Partial<Course>,
      required: true,
    },
    buttonLabel: { type: String, default: "Submit" },
  },
  emits: ["submit"],
  data() {
    return {
      localCourse: { ...this.initialCourse } as Course,
      hasTriedSubmit: false,
      isFormValid: false,
    };
  },
  methods: {
    updateLocal(updated: Course) {
      this.localCourse = updated;
    },
    handleValidationChange(isValid: boolean) {
      this.isFormValid = isValid;
    },
    submit() {
      this.hasTriedSubmit = true;

      if (this.isFormValid) {
        this.$emit("submit", { ...this.localCourse });
      }
    },
  },
});
</script>
