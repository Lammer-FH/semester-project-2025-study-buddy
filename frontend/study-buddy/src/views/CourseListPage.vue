<template>
  <base-layout page-title="Courses">
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button @view-course="goToCreateCourse">
          <ion-icon :icon="add" slot="start" />
          Create
        </ion-button>
      </ion-buttons>
    </template>
    <app-spinner v-if="loading" />
    <error-message v-if="error" :message="error" @dismiss="clearError" />
    <course-list
      v-else
      :courses="courses"
      @delete="confirmDelete"
      @edit="goToEditPage"
      @view-course="goToAssignment"
    />
    <confirm-dialog
      :visible="showDialog"
      :title="`${courseTitleToDelete}`"
      :message="`Are you sure you want to delete this course?`"
      @confirm="deleteCourse"
      @cancel="showDialog = false"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CourseList from "@/components/organisms/CourseList.vue";
import { useCourseStore } from "@/stores/courseStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import ErrorMessage from "@/components/atoms/ErrorMessage.vue";
import { Course } from "@/types/course";
import { add } from "ionicons/icons";
import ConfirmDialog from "@/components/atoms/ConfirmDialog.vue";
import { IonButton, IonButtons, IonIcon } from "@ionic/vue";

export default defineComponent({
  components: {
    CourseList,
    AppSpinner,
    ErrorMessage,
    ConfirmDialog,
    IonButton,
    IonButtons,
    IonIcon,
  },
  data() {
    return {
      courseStore: useCourseStore(),
      add,
      courseIdToDelete: null as number | null,
      courseTitleToDelete: "",
      showDialog: false,
    };
  },
  computed: {
    courses(): Course[] {
      return this.courseStore.list;
    },
    loading(): boolean {
      return this.courseStore.isLoading;
    },
    error(): string | null {
      return this.courseStore.error;
    },
  },
  methods: {
    clearError() {
      this.courseStore.error = null;
    },
    goToCreateCourse() {
      this.$router.push("/tabs/course/new");
    },
    confirmDelete(courseId: number) {
      const course = this.courses.find((c) => c.id === courseId);
      if (course) {
        this.courseIdToDelete = courseId;
        this.courseTitleToDelete = course.title;
        this.showDialog = true;
      }
    },
    async deleteCourse() {
      if (this.courseIdToDelete !== null) {
        await this.courseStore.deleteCourse(this.courseIdToDelete);
        this.courseIdToDelete = null;
        this.courseTitleToDelete = "";
        this.showDialog = false;
      }
    },
    goToAssignment(courseId: number) {
      this.$router.push(`/tabs/course/${courseId}`);
    },
    goToEditPage(courseId: number) {
      this.$router.push(`/tabs/course/${courseId}/edit`);
    },
  },
  async mounted() {
    await this.courseStore.listAll();
  },
});
</script>
