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
      loading: true,
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

  methods: {
    async created() {
      await this.loadData();
    },
    async loadData() {
      console.log("AssignmentEditPage created");
      console.log("Assignment ID:", this.assignmentId);

      try {
        // Load courses first to prevent the "Course not found" error
        const courseStore = useCourseStore();
        console.log("Loading courses...");
        await courseStore.listAll();
        this.courses = courseStore.list;
        console.log("Courses loaded:", this.courses.length);

        // Load assignments
        const assignmentStore = useAssignmentStore();
        console.log("Loading assignments...");
        await assignmentStore.listAll();
        console.log("Assignments loaded:", assignmentStore.list.length);

        // Find the assignment
        const foundAssignment = assignmentStore.list.find(
          (a: Assignment) => a.id === this.assignmentId
        );

        if (foundAssignment) {
          this.assignment = { ...foundAssignment };
          console.log("Assignment found:", this.assignment);
        } else {
          console.error("Assignment not found with ID:", this.assignmentId);
          // Redirect to assignment list if assignment not found
          this.$router.replace("/tabs/assignment-list");
        }
      } catch (error) {
        console.error("Error loading data:", error);
        this.$router.replace("/tabs/assignment-list");
      } finally {
        this.loading = false;
      }
    },
    async ionViewWillEnter() {
      console.log("ionViewWillEnter fired ✅");
      this.assignment = null;
      this.loading = true;
      await this.loadData();
    },
    async handleUpdate(updatedAssignment: Assignment) {
      console.log("Updating assignment:", updatedAssignment);

      try {
        const store = useAssignmentStore();
        const { id, ...updatedData } = updatedAssignment;

        // Ensure proper date formatting
        updatedData.deadline = updatedAssignment.deadline
          ? new Date(updatedAssignment.deadline).toISOString().split("T")[0]
          : "";

        console.log("Updating with data:", updatedData);
        await store.update(id, updatedData);

        console.log("Update successful, navigating back...");
        this.$router.push(this.defaultBackLink);
      } catch (error) {
        console.error("Error updating assignment:", error);
      }
    },
  },
});
</script>
