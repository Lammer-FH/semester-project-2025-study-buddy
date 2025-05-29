<template>
  <base-layout page-title="Course MMI">
    <course-list-item title="MMI"></course-list-item>
    <div v-if="loading">Loading Assignments…</div>
    <assignment-list v-else :assignments="assignments"></assignment-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/AssignmentList.vue";
import { Assignment } from "@/types/assignment";
import { useCourseStore } from "@/stores/courseStore";

export default defineComponent({
  name: "CoursePage",
  components: {
    AssignmentList,
  },
  data() {
    return {
      courseStore: useCourseStore(),
    };
  },
  computed: {
    assignments(): Assignment[] {
      return this.courseStore.currentCourseAssignments;
    },
    loading() {
      return this.courseStore.isAssignmentListLoading;
    },
  },
  mounted() {
    this.courseStore.selectCourse(Number(this.$route.params.id));
    this.courseStore.listAllAssignmentsOfCurrentCourse();
  },
});
</script>
