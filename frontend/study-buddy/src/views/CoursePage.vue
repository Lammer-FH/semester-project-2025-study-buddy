<template>
  <base-layout page-title="Course MMI">
    <course-list-item title="MMI"></course-list-item>
    <div v-if="loading">Loading Assignments…</div>
    <assignment-list v-else :assignments="assignments"></assignment-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import AssignmentList from '@/components/AssignmentList.vue';
import { Assignment } from '@/types/assignment'
import { getAssignmentByCourseId } from '@/services/courseService';

export default defineComponent({
  name: 'CoursePage',
  components: {
    AssignmentList
  },
  data() {
    return {
      courseId: Number(this.$route.params.id),
      assignments: [] as Assignment[],
      loading: true

    }
  },
  methods: {
  async loadAssignments() {
    if (this.courseId != null) {
      this.loading = true
      this.assignments = await getAssignmentByCourseId(this.courseId)
      this.loading = false
      }
    }
  },
  mounted(){
   this.loadAssignments()
  },
})
</script>

