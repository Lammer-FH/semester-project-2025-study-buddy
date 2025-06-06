// src/stores/courseStore.ts
import { defineStore } from "pinia";
import type { Course } from "@/types/course";
import type { Assignment } from "@/types/assignment";
import {
  getCourses,
  getAssignmentsByCourseId,
  createCourse,
  updateCourse,
  deleteCourse,
} from "@/services/courseService";
import type { PersistenceOptions } from "pinia-plugin-persistedstate";

export const useCourseStore = defineStore("course", {
  state: () => ({
    list: [] as Course[],
    currentCourse: null as Course | null,
    currentCourseAssignments: [] as Assignment[],
    isLoading: false,
    isAssignmentListLoading: false,
    error: null as string | null,
  }),

  actions: {
    // Fetch all courses
    async listAll(): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        this.list = await getCourses();
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Error loading courses", error);
      } finally {
        this.isLoading = false;
      }
    },

    // Create a new course
    async createCourse(courseData: Omit<Course, "id">): Promise<Course> {
      this.isLoading = true;
      this.error = null;
      try {
        const newCourse = await createCourse(courseData);
        this.list.push(newCourse);
        return newCourse;
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Error creating course", error);
        throw error;
      } finally {
        this.isLoading = false;
      }
    },

    // Update an existing course
    async updateCourse(updatedCourse: Course): Promise<Course> {
      this.isLoading = true;
      this.error = null;
      try {
        const result = await updateCourse(updatedCourse.id, updatedCourse);

        // Update in local state
        const index = this.list.findIndex(
          (c: Course) => c.id === updatedCourse.id
        );
        if (index !== -1) {
          this.list[index] = result;
        }

        // Update current course if it's the one being edited
        if (this.currentCourse?.id === updatedCourse.id) {
          this.currentCourse = result;
        }

        return result;
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Error updating course", error);
        throw error;
      } finally {
        this.isLoading = false;
      }
    },

    // Delete a course
    async deleteCourse(id: number): Promise<void> {
      this.isLoading = true;
      this.error = null;
      try {
        await deleteCourse(id);
        this.list = this.list.filter((course: Course) => course.id !== id);
        if (this.currentCourse?.id === id) {
          this.currentCourse = null;
          this.currentCourseAssignments = [];
        }
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Error deleting course", error);
        throw error;
      } finally {
        this.isLoading = false;
      }
    },

    // Set the current course by ID
    selectCourse(id: number): void {
      const found = this.list.find((course: Course) => course.id === id);
      this.currentCourse = found ?? null;
      if (!found) {
        console.warn(`Course with ID ${id} not found in loaded courses`);
      }
    },

    // Fetch assignments for the currently selected course
    async listAllAssignmentsOfCurrentCourse(): Promise<void> {
      if (!this.currentCourse) {
        console.warn("No course selected.");
        return;
      }

      this.isAssignmentListLoading = true;
      this.error = null;
      try {
        this.currentCourseAssignments = await getAssignmentsByCourseId(
          this.currentCourse.id
        );
      } catch (error) {
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Failed to fetch course assignments", error);
        this.currentCourseAssignments = [];
      } finally {
        this.isAssignmentListLoading = false;
      }
    },
  },

  persist: {
    key: "course-store",
    storage: localStorage,
    paths: ["list", "currentCourse", "currentCourseAssignments"],
  } as PersistenceOptions,
});
