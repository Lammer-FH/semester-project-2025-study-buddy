<template>
  <base-layout page-title="Assignments">
    <div v-if="loading">Loading Assignments…</div>
    <assignment-list v-else :assignments="assignments"></assignment-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import AssignmentList from '@/components/AssignmentList.vue';
import { Assignment } from '@/types/assignment'
import { useAssignmentStore } from '@/stores/AssignmentStore';

export default defineComponent({
  components: {
    AssignmentList
  },
  data() {
    return {
      assignmentStore: useAssignmentStore()

    }
  },
  computed:{
    assignments(): Assignment[]{
      return this.assignmentStore.list;
    },
    loading(){
      return this.assignmentStore.isLoading;
    }
  },
  mounted() {
    this.assignmentStore.listAll();
  }
})
</script>
