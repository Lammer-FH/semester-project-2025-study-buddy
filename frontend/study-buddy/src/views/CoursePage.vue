<template>
  <base-layout :page-title="courseTitle">
    <app-spinner v-if="loading"></app-spinner>
    <assignment-list v-else :assignments="assignments" />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/AssignmentList.vue";
import { useCourseStore } from "@/stores/courseStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";

export default defineComponent({
  name: "CoursePage",
  components: {
    AssignmentList,
    AppSpinner,
  },
  data() {
    return {
      courseStore: useCourseStore(),
    };
  },
  computed: {
    assignments() {
      return this.courseStore.currentCourseAssignments;
    },
    courseTitle() {
      return this.courseStore.currentCourse?.title ?? "Course";
    },
    loading() {
      return this.courseStore.isAssignmentListLoading;
    },
  },
  async mounted() {
    await this.loadCourseData();
  },
  watch: {
    "$route.params.id"() {
      this.loadCourseData();
    },
  },
  methods: {
    async loadCourseData() {
      const id = Number(this.$route.params.id);
      await this.courseStore.selectCourse(id);
      if (this.courseStore.currentCourse) {
        await this.courseStore.listAllAssignmentsOfCurrentCourse();
      }
    },
  },
});
</script>
