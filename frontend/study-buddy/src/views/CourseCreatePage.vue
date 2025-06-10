<template>
  <base-layout
    page-title="Create Course"
    :show-back-button="true"
    default-back-link="/tabs/course-list"
  >
    <course-form-wrapper
      :key="$route.fullPath"
      :initialCourse="emptyCourse"
      button-label="Create Course"
      @submit="handleCreate"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormWrapper from "@/components/molecules/CourseFormWrapper.vue";
import { useCourseStore } from "@/stores/courseStore";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseCreatePage",
  components: { CourseFormWrapper },
  data() {
    return {
      emptyCourse: { title: "", description: "" } as Partial<Course>,
      store: useCourseStore(),
    };
  },
  methods: {
    async handleCreate(courseInput: Partial<Course>) {
      await this.store.createCourse(courseInput as Omit<Course, "id">);
      this.$router.push("/tabs/course-list");
    },
  },
});
</script>
