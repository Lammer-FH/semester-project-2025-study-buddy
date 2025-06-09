import { defineStore } from "pinia";
import type { Assignment } from "@/types/assignment";
import {
  getAssignments,
  getAssignmentById,
  createAssignment,
  updateAssignment,
  deleteAssignment,
} from "@/services/assignmentService";
import type { PersistenceOptions } from "pinia-plugin-persistedstate";

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

    async fetchById(id: number): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        this.currentAssignment = await getAssignmentById(id);
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    getAssignment(id: number): void {
      const found = this.list.find((item: Assignment) => item.id === id);
      this.currentAssignment = found ?? null;
    },

    async create(
      courseId: number,
      newAssignment: Omit<Assignment, "id">
    ): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        const created = await createAssignment(courseId, newAssignment);
        this.list.push(created);
        this.currentAssignment = created;
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    async update(
      id: number,
      updatedData: Omit<Assignment, "id">
    ): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        const updated = await updateAssignment(id, updatedData);
        const index = this.list.findIndex((a: Assignment) => a.id === id);
        if (index !== -1) {
          this.list[index] = updated;
        }
        this.currentAssignment = updated;
        this.sortAssignmentsByDeadline();
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },
    sortAssignmentsByDeadline() {
      this.list.sort((a: Assignment, b: Assignment) => {
        const dateA = new Date(a.deadline || "").getTime();
        const dateB = new Date(b.deadline || "").getTime();
        return dateA - dateB;
      });
    },
    async selectAssignment(id: number) {
      // If courses are empty, load them first
      if (this.list.length === 0) {
        console.log("Assignments not loaded, loading first...");
        await this.listAll();
      }

      const assignment = this.list.find((c: Assignment) => c.id === id);
      if (assignment) {
        this.currentAssignment = assignment;
        console.log("Assignment selected:", assignment);
      } else {
        console.error(
          `Assignment with ID ${id} not found in loaded assignments`
        );
        // Don't throw error, just log it
        this.currentCourse = null;
      }
    },

    async remove(id: number): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        await deleteAssignment(id);
        this.list = this.list.filter((a: Assignment) => a.id !== id);
        if (this.currentAssignment?.id === id) {
          this.currentAssignment = null;
        }
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },
  },

  persist: {
    key: "assignment-store",
    storage: localStorage,
    paths: ["list", "currentAssignment"],
  } as PersistenceOptions,
});
