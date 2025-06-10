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
import { IonButton } from "@ionic/vue";

export default defineComponent({
  name: "CourseFormWrapper",
  components: { CourseFormFields, IonButton },
  props: {
    initialCourse: {
      type: Object as () => Partial<Course>,
      required: true,
    },
    buttonLabel: { type: String, default: "Submit" },
  },
  emits: ["submit", "form-change", "validation-change"],
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
      console.log("updated local");
      this.$emit("form-change", updated);
    },
    handleValidationChange(isValid: boolean) {
      this.isFormValid = isValid;
      console.log("form is valid: ", isValid);
      // this.$emit("validation-change", isValid);
    },
    submit() {
      this.hasTriedSubmit = true;
      console.log("course submit");
      console.log("course submit again");
      this.$emit("submit", { ...this.localCourse });
    },
  },
});
</script>
