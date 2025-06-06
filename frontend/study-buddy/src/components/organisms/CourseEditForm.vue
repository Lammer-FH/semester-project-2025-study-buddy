<template>
  <div v-if="course">
    <course-form-fields :course="course" @update:course="handleCourseUpdate" />
    <ion-button expand="block" @click="submitEdit"> Save Changes </ion-button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseFormFields from "@/components/molecules/CourseFormFields.vue";
import { useCourseStore } from "@/stores/courseStore";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "CourseEditForm",
  components: {
    CourseFormFields,
  },
  props: {
    courseId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      course: null as Course | null,
    };
  },
  methods: {
    async loadCourse() {
      const store = useCourseStore();
      await store.listAll();
      store.selectCourse(this.courseId);
      this.course = store.currentCourse ? { ...store.currentCourse } : null;
    },
    handleCourseUpdate(updated: Course) {
      this.course = updated;
    },
    async submitEdit() {
      if (!this.course) return;
      const store = useCourseStore();
      await store.updateCourse(this.course);
      this.$router.push("/tabs/course-list");
    },
  },
  created() {
    this.loadCourse();
  },
});
</script>
