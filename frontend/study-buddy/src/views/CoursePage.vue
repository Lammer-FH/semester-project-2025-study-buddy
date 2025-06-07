<template>
  <base-layout
    :page-title="courseTitle"
    :show-back-button="true"
    :default-back-link="'/tabs/course-list'"
  >
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button @click="goToCreateAssignment">
          <ion-icon :icon="add" slot="start" />
          Add Assignment
        </ion-button>
      </ion-buttons>
    </template>
    <app-spinner v-if="loading"></app-spinner>
    <assignment-list
      v-else-if="assignments.length > 0"
      :assignments="assignments"
      @edit="handleEdit"
      @delete="confirmDelete"
    />
    <div v-else-if="!loading" class="ion-padding">
      <p>No assignments found for this course.</p>
    </div>
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
import { useCourseStore } from "@/stores/courseStore";
import { useAssignmentStore } from "@/stores/assignmentStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import ConfirmDialog from "@/components/atoms/ConfirmDialog.vue";
import { add } from "ionicons/icons";

export default defineComponent({
  name: "CoursePage",
  components: {
    AssignmentList,
    AppSpinner,
    ConfirmDialog,
  },
  data() {
    return {
      courseStore: useCourseStore(),
      assignmentStore: useAssignmentStore(),
      add,
      assignmentIdToDelete: null as number | null,
      assignmentTitleToDelete: "",
      showDialog: false,
    };
  },
  computed: {
    assignments() {
      return this.courseStore.currentCourseAssignments;
    },
    courseTitle() {
      return this.courseStore.currentCourse?.title ?? "Course";
    },
    loading() {
      return this.courseStore.isAssignmentListLoading;
    },
    courseId(): number {
      return Number(this.$route.params.id);
    },
  },
  async mounted() {
    await this.loadCourseData();
  },
  watch: {
    "$route.params.id"() {
      this.loadCourseData();
    },
  },
  methods: {
    async loadCourseData() {
      const id = this.getValidCourseId();
      if (id === null) {
        this.router.replace("/tabs/course-list");
        return;
      }

      await this.courseStore.selectCourse(id);
      if (this.courseStore.currentCourse) {
        await this.courseStore.listAllAssignmentsOfCurrentCourse();
      }
    },
    getValidCourseId(): number | null {
      const id = Number(this.$route.params.id);
      return isNaN(id) ? null : id;
    },
    goToCreateAssignment() {
      // Navigate to create assignment with pre-selected course
      this.$router.push(`/tabs/assignment/new?courseId=${this.courseId}`);
    },
    handleEdit(assignmentId: number) {
      this.$router.push({
        path: `/tabs/assignment/${assignmentId}/edit`,
        query: { from: `/tabs/course/${this.courseId}` },
      });
    },
    confirmDelete(assignmentId: number) {
      const assignment = this.assignments.find((a) => a.id === assignmentId);
      if (assignment) {
        this.assignmentIdToDelete = assignmentId;
        this.assignmentTitleToDelete = assignment.title;
        this.showDialog = true;
      }
    },
    async deleteAssignment() {
      if (this.assignmentIdToDelete !== null) {
        await this.assignmentStore.deleteAssignment(this.assignmentIdToDelete);
        // Refresh the assignments for this course
        await this.courseStore.listAllAssignmentsOfCurrentCourse();
        this.assignmentIdToDelete = null;
        this.assignmentTitleToDelete = "";
        this.showDialog = false;
      }
    },
  },
});
</script>
