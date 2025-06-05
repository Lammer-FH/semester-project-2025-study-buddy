<template>
  <base-layout page-title="Assignments">
    <div v-if="loading">Loading Assignments…</div>
    <assignment-list v-else :assignments="assignments" />
  </base-layout>
</template>

<script lang="ts">
import { useAssignmentStore } from '@/stores/assignmentStore';

export default {
  data() {
    const assignmentStore = useAssignmentStore();
    return { assignmentStore };
  },
  computed: {
    assignments() {
      return this.assignmentStore.list;
    },
    loading() {
      return this.assignmentStore.isLoading;
    },
  },
  mounted() {
    this.assignmentStore.listAll();
  }
}
</script>
