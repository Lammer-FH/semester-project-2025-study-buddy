<template>
  <base-layout
    page-title="Edit Course"
    :show-back-button="true"
    default-back-link="/tabs/course-list"
  >
    <course-form-wrapper
      v-if="course"
      :initialCourse="course"
      button-label="Save Changes"
      @submit="handleUpdate"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormWrapper from "@/components/molecules/CourseFormWrapper.vue";
import { useCourseStore } from "@/stores/courseStore";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseEditPage",
  components: { CourseFormWrapper },
  data() {
    return {
      course: null as Course | null,
    };
  },
  computed: {
    courseId(): number {
      const idParam = this.$route.params.id;
      return parseInt(Array.isArray(idParam) ? idParam[0] : idParam);
    },
  },
  async created() {
    const store = useCourseStore();
    if (!store.list.length) {
      await store.listAll();
    }
    store.selectCourse(this.courseId);
    this.course = store.currentCourse ? { ...store.currentCourse } : null;
  },
  methods: {
    async handleUpdate(updatedCourse: Course) {
      const store = useCourseStore();
      await store.updateCourse(updatedCourse);
      this.$router.push("/tabs/course-list");
    },
  },
});
</script>
