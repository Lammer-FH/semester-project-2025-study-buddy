<template>
  <base-layout page-title="Assignments">
    <app-spinner v-if="loading"></app-spinner>
    <error-message v-if="error" :message="error" @dismiss="clearError" />
    <assignment-list v-else :assignments="assignments"></assignment-list>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/AssignmentList.vue";
import { Assignment } from "@/types/assignment";
import { useAssignmentStore } from "@/stores/assignmentStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import ErrorMessage from "@/components/atoms/ErrorMessage.vue";

export default defineComponent({
  components: {
    AssignmentList,
    AppSpinner,
    ErrorMessage,
  },
  data() {
    return {
      assignmentStore: useAssignmentStore(),
    };
  },
  computed: {
    assignments(): Assignment[] {
      return this.assignmentStore.list;
    },
    loading() {
      return this.assignmentStore.isLoading;
    },
    error(): string | null {
      return this.assignmentStore.error;
    },
  },
  methods: {
    clearError() {
      this.assignmentStore.error = null;
    },
  },
  mounted() {
    this.assignmentStore.listAll();
  },
});
</script>
