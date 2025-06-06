<template>
  <base-layout
    :page-title="courseTitle"
    :show-back-button="true"
    :default-back-link="'/tabs/course-list'"
  >
    <app-spinner v-if="loading"></app-spinner>
    <assignment-list
      v-else-if="assignments.length > 0"
      :assignments="assignments"
    />
    <div v-else-if="!loading" class="ion-padding">
      <p>No assignments found for this course.</p>
    </div>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/organisms/AssignmentList.vue";
import { useCourseStore } from "@/stores/courseStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "CoursePage",
  components: {
    AssignmentList,
    AppSpinner,
  },
  data() {
    return {
      courseStore: useCourseStore(),
      router: useRouter(),
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
  },
});
</script>
