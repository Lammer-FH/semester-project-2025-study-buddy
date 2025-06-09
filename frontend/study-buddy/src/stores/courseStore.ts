// src/stores/courseStore.ts
import { defineStore } from "pinia";
import type { Course } from "@/types/course";
import {
  getCourses,
  createCourse,
  updateCourse,
  deleteCourse,
} from "@/services/courseService";
import type { PersistenceOptions } from "pinia-plugin-persistedstate";

export const useCourseStore = defineStore("course", {
  state: () => ({
    list: [] as Course[],
    currentCourseId: null as number | null,
    currentCourse: null as Course | null,
    isLoading: false,
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
        this.list = [];
        this.error = error instanceof Error ? error.message : String(error);
        console.error("Error loading courses", error);
      } finally {
        this.isLoading = false;
      }
    },
    async getCourse(id: number): Promise<void> {
      const found = this.list.find((item: Course) => item.id === id);
      this.currentCourse = found ?? null;
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
          this.currentCourseId = null;
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
    async selectCourse(id: number) {
      // If courses are empty, load them first
      if (this.list.length === 0) {
        console.log("Courses not loaded, loading first...");
        await this.listAll();
      }
      const course = this.list.find((c: Course) => c.id === id);
      if (course) {
        this.currentCourseId = course.id;
        console.log("Course selected:", course);
      } else {
        console.error(`Course with ID ${id} not found in loaded courses`);
        this.currentCourseId = null;
      }
    },
    clearData() {
      this.currentCourseId = null;
    },
  },

  persist: {
    key: "course-store",
    storage: localStorage,
    paths: [
      "list",
      "currentCourse",
      "currentCourseId",
      "currentCourseAssignments",
    ],
  } as PersistenceOptions,
});
