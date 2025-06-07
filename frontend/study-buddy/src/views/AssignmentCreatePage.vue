<template>
  <base-layout
    page-title="Create Assignment"
    :show-back-button="true"
    default-back-link="/tabs/assignments"
  >
    <assignment-form-wrapper
      v-if="courses.length > 0"
      :key="$route.fullPath"
      :initialAssignment="emptyAssignment"
      :courses="courses"
      button-label="Create Assignment"
      @submit="handleCreate"
    />
    <app-spinner v-else />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentFormWrapper from "@/components/molecules/AssignmentFormWrapper.vue";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import { useAssignmentStore } from "@/stores/assignmentStore";
import { useCourseStore } from "@/stores/courseStore";
import type { Assignment } from "@/types/assignment";
import type { Course } from "@/types/course";

export default defineComponent({
  name: "AssignmentCreatePage",
  components: { AssignmentFormWrapper, AppSpinner },
  data() {
    return {
      emptyAssignment: {
        title: "",
        description: "",
        deadline: new Date().toISOString(),
        courseId: null,
      } as Partial<Assignment>,
      courses: [] as Course[],
    };
  },
  computed: {
    courseId(): number | null {
      const id = this.$route.query.courseId;
      return id ? Number(id) : null;
    },
  },
  async created() {
    const courseStore = useCourseStore();
    if (!courseStore.list.length) {
      await courseStore.listAll();
    }
    this.courses = courseStore.list;
  },
  methods: {
    async handleCreate(assignmentInput: Partial<Assignment>) {
      const store = useAssignmentStore();
      await store.createAssignment(assignmentInput as Omit<Assignment, "id">);
      if (this.courseId) {
        this.$router.push(`/tabs/course/${this.courseId}`);
      } else {
        this.$router.push("/tabs/assignment-list");
      }
    },
  },
});
</script>
