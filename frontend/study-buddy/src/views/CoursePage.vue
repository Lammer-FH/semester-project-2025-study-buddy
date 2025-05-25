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
import { getAssignments, Assignment } from '@/services/assignmentService'

export default defineComponent({
  components: {
    AssignmentList
  },
  data() {
    return {
      assignments: [] as Assignment[],
      loading: true

    }
  },
  mounted() {
    getAssignments().then(data => {
      this.assignments = data
      this.loading = false
    })
  }
})
</script>

