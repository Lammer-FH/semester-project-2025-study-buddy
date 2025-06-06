<template>
  <course-form-wrapper
    :initialCourse="emptyCourse"
    button-label="Create Course"
    @submit="submitCreate"
  />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormWrapper from "./CourseFormWrapper.vue";
import { useCourseStore } from "@/stores/courseStore";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseCreateForm",
  components: { CourseFormWrapper },
  data() {
    return {
      emptyCourse: {
        id: 0,
        title: "",
        description: "",
      } as Course,
    };
  },
  methods: {
    async submitCreate(newCourse: Course) {
      const store = useCourseStore();
      await store.createCourse({
        title: newCourse.title,
        description: newCourse.description,
      });
      this.$router.push("/tabs/courses");
    },
  },
});
</script>
