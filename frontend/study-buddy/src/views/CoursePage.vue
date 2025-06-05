<template>
  <base-layout :page-title="courseTitle">
    <div v-if="loading">Loading Assignments…</div>
    <assignment-list v-else :assignments="assignments" />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import AssignmentList from '@/components/AssignmentList.vue';
import { useCourseStore } from '@/stores/courseStore';

export default defineComponent({
  name: 'CoursePage',
  components: {
    AssignmentList,
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
      return this.courseStore.currentCourse?.title ?? 'Course';
    },
    loading() {
      return this.courseStore.isAssignmentListLoading;
    },
  },
  async mounted() {
    await this.loadCourseData();
  },
  watch: {
    '$route.params.id'() {
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
