<template>
  <base-layout
    page-title="Edit Assignment"
    :show-back-button="true"
    :default-back-link="defaultBackLink"
  >
    <assignment-form-wrapper
      v-if="assignment && !loading"
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
import type { Assignment } from "@/types/assignment";

export default defineComponent({
  name: "AssignmentEditPage",
  components: { AssignmentFormWrapper, AppSpinner },
  data() {
    return {
      assignmentStore: useAssignmentStore(),
      assignment: null as Assignment | null,
      loading: true,
    };
  },
  computed: {
    assignmentId(): number {
      return Number(this.$route.params.id);
    },
    defaultBackLink(): string {
      const from = this.$route.query.from;
      return typeof from === "string" && from.startsWith("/")
        ? from
        : "/tabs/assignment-list";
    },
  },
  // async created() {
  // },
  async ionViewWillEnter() {
    console.log("ionViewWillEnter fired ✅");
    this.assignment = null;
    this.loading = true;
    await this.loadData();
  },
  methods: {
    async loadData() {
      console.log("Assignment ID:", this.assignmentId);
      try {
        this.assignmentStore.getAssignment(this.assignmentId);

        if (this.assignmentStore.currentAssignment) {
          this.assignment = this.assignmentStore.currentAssignment;
          console.log("Assignment found:", this.assignment);
        } else {
          console.error("Assignment not found with ID:", this.assignmentId);
          this.$router.replace("/tabs/assignment-list");
        }
      } catch (error) {
        console.error("Error loading data:", error);
        this.$router.replace("/tabs/assignment-list");
      } finally {
        this.loading = false;
      }
    },
    async handleUpdate(updatedAssignment: Assignment) {
      console.log("Updating assignment:", updatedAssignment);

      try {
        const { id, ...updatedData } = updatedAssignment;
        await this.assignmentStore.update(id, updatedData);
        console.log("Update successful, navigating back...");
        this.$router.push(this.defaultBackLink);
      } catch (error) {
        console.error("Error updating assignment:", error);
        // You might want to show an error message to the user here
      }
    },
  },
});
</script>
