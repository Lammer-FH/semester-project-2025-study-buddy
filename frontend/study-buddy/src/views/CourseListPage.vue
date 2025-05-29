<template>
  <base-layout page-title="Courses">
    <div v-if="loading">Loading Courses...</div>
    <course-list v-else :courses="courses"></course-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import CourseList from '@/components/CourseList.vue'
import { getCourses } from '@/services/courseService'
import {Course} from '@/types/course'
import { useCourseStore } from '@/stores/courseStore';

export default defineComponent({
  components: {
    CourseList
  },
  data() {
    return {
      courseStore: useCourseStore()
    }
  },
  computed:{
    courses(): Course[]{
      return this.courseStore.list;
    },
    loading(){
      return this.courseStore.isLoading;
    }
  },
  mounted() {
    getCourses().then((data) => {
      this.courseStore.listAll();
    })
  }
})
</script>
