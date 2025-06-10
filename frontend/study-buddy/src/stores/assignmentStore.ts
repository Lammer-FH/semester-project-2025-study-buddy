import { defineStore } from "pinia";
import type { Assignment } from "@/types/assignment";
import {
  getAssignments,
  getAssignmentById,
  createAssignment,
  updateAssignment,
  deleteAssignment,
  getAssignmentsByCourse,
} from "@/services/assignmentService";
import type { PersistenceOptions } from "pinia-plugin-persistedstate";

export const useAssignmentStore = defineStore("assignment", {
  state: () => ({
    list: [] as Assignment[],
    currentAssignmentId: null as number | null,
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

    async getAssignment(id: number): Promise<void> {
      const found = this.list.find((item: Assignment) => item.id === id);
      console.log("found Assignment", found);
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
        console.log("update done", updated);
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
        this.currentAssignmentId = assignment.id;
        console.log("Assignment selected:", assignment);
      } else {
        console.error(
          `Assignment with ID ${id} not found in loaded assignments`
        );
        // Don't throw error, just log it
        this.currentAssignmentId = null;
      }
    },
    // Fetch assignments for the currently selected course
    async fetchAllAssignmentByCourseId(id: number): Promise<void> {
      if (!id) {
        console.warn("No course selected.");
        return;
      }
      this.isLoading = true;
      this.error = null;
      try {
        this.list = await getAssignmentsByCourse(id);
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Failed to fetch course assignments", error);
        this.list = [];
      } finally {
        this.isLoading = false;
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
          this.currentAssignmentId = null;
        }
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },
    clearData() {
      this.list = [];
    },
  },

  persist: {
    key: "assignment-store",
    storage: localStorage,
    paths: ["list", "currentAssignment", "currentAssignmentId"],
  } as PersistenceOptions,
});
