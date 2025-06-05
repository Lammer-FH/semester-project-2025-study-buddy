import { defineStore } from "pinia";
import type { Assignment } from "@/types/assignment";
import { getAssignments } from "@/services/assignmentService";
export const useAssignmentStore = defineStore("assignment", {
  state: () => ({
    list: [] as Assignment[],
    currentAssignment: null as Assignment | null,
    isLoading: false,
    error: null as string | null,
  }),
  actions: {
    async listAll(): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        this.list = await getAssignments();
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    getAssignment(id: number): void {
      const found = this.list.find((item) => item.id === id);
      this.currentAssignment = found ?? null;
    },
  },
});
