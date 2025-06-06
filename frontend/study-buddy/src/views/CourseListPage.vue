<template>
  <base-layout page-title="Courses">
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button @click="goToCreateCourse">
          <ion-icon :icon="add" slot="start" />
          Create
        </ion-button>
      </ion-buttons>
    </template>
    <app-spinner v-if="loading" />
    <error-message v-if="error" :message="error" @dismiss="clearError" />
    <course-list v-else :courses="courses" />
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

export default defineComponent({
  components: {
    CourseList,
    AppSpinner,
    ErrorMessage,
  },
  data() {
    return {
      courseStore: useCourseStore(),
      add,
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
  },
  async mounted() {
    if (this.courses.length === 0) {
      await this.courseStore.listAll();
    }
  },
});
</script>
