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
      store: useCourseStore(),
    };
  },
  computed: {
    courseId(): number {
      return Number(this.$route.params.id);
    },
  },
  async created() {
    this.loadData();
  },
  methods: {
    async loadData() {
      if (!this.store.list.length) {
        await this.store.listAll();
      }
      // this.store.selectCourse(this.courseId);
      await this.store.getCourse(this.courseId);
      this.course = this.store.currentCourse
        ? { ...this.store.currentCourse }
        : null;
    },
    async ionViewDidLeave() {
      console.log("ionViewWillEnter fired ✅");
      this.course = null;
      // await this.loadData();
    },
    async handleUpdate(updatedCourse: Course) {
      console.log("trying to update course to", updatedCourse.title);
      await this.store.updateCourse(updatedCourse);
      this.$router.push("/tabs/course-list");
    },
  },
});
</script>
