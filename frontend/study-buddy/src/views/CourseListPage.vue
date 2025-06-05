<template>
  <base-layout page-title="Courses">
    <div v-if="loading">Loading Courses...</div>
    <course-list v-else :courses="courses" />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import CourseList from '@/components/CourseList.vue';
import { useCourseStore } from '@/stores/courseStore';

export default defineComponent({
  name: 'CourseListPage',
  components: {
    CourseList,
  },
  data() {
    return {
      courseStore: useCourseStore(),
    };
  },
  computed: {
    courses() {
      return this.courseStore.list;
    },
    loading() {
      return this.courseStore.isLoading;
    },
  },
  mounted() {
    this.courseStore.listAll();
  },
});
</script>
