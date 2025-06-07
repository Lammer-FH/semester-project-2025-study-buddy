<template>
  <base-layout
    page-title="Edit Assignment"
    :show-back-button="true"
    :default-back-link="defaultBackLink"
  >
    <assignment-form-wrapper
      v-if="assignment"
      :initialAssignment="assignment"
      button-label="Save Changes"
      @submit="handleUpdate"
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
  name: "AssignmentEditPage",
  components: { AssignmentFormWrapper, AppSpinner },
  data() {
    return {
      assignment: null as Assignment | null,
      courses: [] as Course[],
    };
  },
  computed: {
    assignmentId(): number {
      const idParam = this.$route.params.id;
      return parseInt(Array.isArray(idParam) ? idParam[0] : idParam);
    },
    defaultBackLink(): string {
      const from = this.$route.query.from;
      return typeof from === "string" && from.startsWith("/")
        ? from
        : "/tabs/assignment-list";
    },
  },
  async created() {
    // Load courses first
    const courseStore = useCourseStore();
    if (!courseStore.list.length) {
      await courseStore.listAll();
    }
    this.courses = courseStore.list;

    // Load assignment
    const assignmentStore = useAssignmentStore();
    if (!assignmentStore.list.length) {
      await assignmentStore.listAll();
    }

    const foundAssignment = assignmentStore.list.find(
      (a) => a.id === this.assignmentId
    );
    this.assignment = foundAssignment ? { ...foundAssignment } : null;
  },
  methods: {
    async handleUpdate(updatedAssignment: Assignment) {
      const store = useAssignmentStore();
      const { id, ...updatedData } = updatedAssignment;
      await store.update(id, updatedData);
      this.$router.push(this.defaultBackLink);
    },
  },
});
</script>
