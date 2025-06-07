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
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
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
