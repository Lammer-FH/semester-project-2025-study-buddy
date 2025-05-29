<template>
  <base-layout page-title="Courses">
    <div v-if="loading">Loading Courses...</div>
    <course-list v-else :courses="courses"></course-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import CourseList from '@/components/CourseList.vue';
import { getCourses, Course } from '@/services/courseService'

export default defineComponent({
  components: {
    CourseList
  },
  data() {
    return {
      courses: [] as Course[],
      loading: true

    }
  },
  mounted() {
    getCourses().then(data => {
      this.courses = data
      console.log(data)
      this.loading = false
    })
  }
})
</script>
