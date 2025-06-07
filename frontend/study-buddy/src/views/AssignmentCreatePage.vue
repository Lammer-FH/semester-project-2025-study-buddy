<template>
  <base-layout
    page-title="Create Assignment"
    :show-back-button="true"
    :default-back-link="`/tabs/course/${courseId}`"
  >
    <assignment-form-wrapper
      :key="$route.fullPath"
      :initialAssignment="emptyAssignment"
      button-label="Create Assignment"
      @submit="handleCreate"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentFormWrapper from "@/components/molecules/AssignmentFormWrapper.vue";
import { useAssignmentStore } from "@/stores/assignmentStore";
import type { Assignment } from "@/types/assignment";

export default defineComponent({
  name: "AssignmentCreatePage",
  components: { AssignmentFormWrapper },
  data() {
    return {
      emptyAssignment: {
        title: "",
        description: "",
        deadline: new Date().toISOString(),
      } as Partial<Assignment>,
    };
  },
  computed: {
    courseId(): number | null {
      const id = this.$route.query.courseId;
      return id ? Number(id) : null;
    },
  },
  methods: {
    async handleCreate(assignmentInput: Partial<Assignment>) {
      const store = useAssignmentStore();
      if (this.courseId === null) {
        throw new Error("Missing courseId");
      }
      await store.create(
        this.courseId,
        assignmentInput as Omit<Assignment, "id">
      );
      this.$router.replace(`/tabs/course/${this.courseId}`);
    },
  },
});
</script>
