// src/stores/courseStore.ts

import { defineStore } from "pinia";
import type { Course } from "@/types/course";
import type { Assignment } from "@/types/assignment";
import { getCourses, getAssignmentByCourseId } from "@/services/courseService";

export const useCourseStore = defineStore("course", {
  state: () => ({
    list: [] as Course[],
    currentCourse: null as Course | null,
    currentCourseAssignments: [] as Assignment[],
    isLoading: false,
    isAssignmentListLoading: false,
  }),

  actions: {
    // Fetch all courses
    async listAll(): Promise<void> {
      this.isLoading = true;
      try {
        this.list = await getCourses();
      } catch (error) {
        console.error("Error loading courses", error);
      } finally {
        this.isLoading = false;
      }
    },

    // Set the current course by ID
    selectCourse(id: number): void {
      const found = this.list.find((course) => course.id === id);
      this.currentCourse = found ?? null;
    },

    // Fetch assignments for the currently selected course
    async listAllAssignmentsOfCurrentCourse(): Promise<void> {
      if (!this.currentCourse) {
        console.warn("No course selected.");
        return;
      }

      this.isAssignmentListLoading = true;
      try {
        this.currentCourseAssignments = await getAssignmentByCourseId(
          this.currentCourse.id
        );
      } catch (error) {
        console.error("Failed to fetch course assignments", error);
        this.currentCourseAssignments = [];
      } finally {
        this.isAssignmentListLoading = false;
      }
    },
  },
});
