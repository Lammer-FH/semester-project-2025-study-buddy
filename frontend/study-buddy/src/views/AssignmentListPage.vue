<template>
  <base-layout page-title="Assignments">
    <app-spinner v-if="loading"></app-spinner>
    <error-message v-if="error" :message="error" @dismiss="clearError" />
    <assignment-list
      v-else
      :assignments="assignments"
      @delete="confirmDelete"
      @edit="goToEditPage"
      @view-assignment="goToAssignmentPage"
    ></assignment-list>
    <confirm-dialog
      :visible="showDialog"
      :title="`${assignmentTitleToDelete}`"
      :message="`Are you sure you want to delete this assignment?`"
      @confirm="deleteAssignment"
      @cancel="showDialog = false"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/organisms/AssignmentList.vue";
import { Assignment } from "@/types/assignment";
import { useAssignmentStore } from "@/stores/assignmentStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import ErrorMessage from "@/components/atoms/ErrorMessage.vue";
import ConfirmDialog from "@/components/atoms/ConfirmDialog.vue";

export default defineComponent({
  components: {
    AssignmentList,
    AppSpinner,
    ErrorMessage,
    ConfirmDialog,
  },
  data() {
    return {
      assignmentStore: useAssignmentStore(),
      assignmentIdToDelete: null as number | null,
      assignmentTitleToDelete: "",
      showDialog: false,
    };
  },
  computed: {
    assignments(): Assignment[] {
      return this.assignmentStore.list;
    },
    loading(): boolean {
      return this.assignmentStore.isLoading;
    },
    error(): string | null {
      return this.assignmentStore.error;
    },
  },
  async ionViewWillEnter() {
    await this.assignmentStore.listAll();
  },
  methods: {
    clearError() {
      this.assignmentStore.error = null;
    },
    async confirmDelete(assignmentId: number) {
      await this.assignmentStore.getAssignment(assignmentId);
      if (this.assignmentStore.currentAssignment) {
        this.assignmentIdToDelete = assignmentId;
        this.assignmentTitleToDelete =
          this.assignmentStore.currentAssignment.title;
        this.showDialog = true;
      }
    },
    async deleteAssignment() {
      if (this.assignmentIdToDelete !== null) {
        await this.assignmentStore.remove(this.assignmentIdToDelete);
        this.assignmentIdToDelete = null;
        this.assignmentTitleToDelete = "";
        this.showDialog = false;
      }
    },
    goToEditPage(assignmentId: number) {
      this.assignmentStore.selectAssignment(assignmentId);
      this.$router.push({
        path: `/tabs/assignment/${assignmentId}/edit`,
        query: { from: "/tabs/assignment-list" },
      });
    },
    goToAssignmentPage(assignmentId: number) {
      this.assignmentStore.selectAssignment(assignmentId);
      this.$router.push({
        path: `/tabs/assignment/${assignmentId}/tasks`,
        query: { from: "/tabs/assignment-list" },
      });
    },
  },
  // mounted() {
  //   this.assignmentStore.listAll();
  // },
});
</script>
