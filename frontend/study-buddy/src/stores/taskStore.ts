import { defineStore } from "pinia";
import type { Task } from "@/types/task";
import {
  getTasksByAssignmentId,
  addTaskToAssignment,
  updateTask,
  deleteTask,
} from "@/services/taskService";
import type { PersistenceOptions } from "pinia-plugin-persistedstate";

export const useTaskStore = defineStore("task", {
  state: () => ({
    list: [] as Task[],
    currentTask: null as Task | null,
    isLoading: false,
    error: null as string | null,
  }),

  actions: {
    async fetchByAssignmentId(assignmentId: number): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        this.list = await getTasksByAssignmentId(assignmentId);
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    async create(
      assignmentId: number,
      newTask: Omit<Task, "id">
    ): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        const created = await addTaskToAssignment(assignmentId, newTask);
        this.list.push(created);
        this.currentTask = created;
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    async update(id: number, updatedData: Omit<Task, "id">): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        const updated = await updateTask(id, updatedData);
        const index = this.list.findIndex((t: Task) => t.id === id);
        if (index !== -1) {
          this.list[index] = updated;
        }
        this.currentTask = updated;
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },

    getTask(id: number): void {
      const found = this.list.find((item: Task) => item.id === id);
      this.currentTask = found ?? null;
    },

    async selectTask(id: number) {
      if (this.list.length === 0) {
        console.log("Tasks not loaded, cannot select");
        this.currentTask = null;
        return;
      }

      const task = this.list.find((t: Task) => t.id === id);
      if (task) {
        this.currentTask = task;
      } else {
        console.error(`Task with ID ${id} not found in loaded tasks`);
        this.currentTask = null;
      }
    },

    async remove(id: number): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        await deleteTask(id);
        this.list = this.list.filter((t: Task) => t.id !== id);
        if (this.currentTask?.id === id) {
          this.currentTask = null;
        }
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
      } finally {
        this.isLoading = false;
      }
    },
  },

  persist: {
    key: "task-store",
    storage: localStorage,
    paths: ["list", "currentTask"],
  } as PersistenceOptions,
});
