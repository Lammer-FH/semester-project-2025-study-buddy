import { defineStore } from "pinia";
import type { Course } from "@/types/course";
import { getCourses, getAssignmentByCourseId } from "@/services/courseService";
import { Assignment } from "@/types/assignment";
export const useCourseStore = defineStore("course", {
  state: () => ({
    list: [] as Course[],
    currentCourse: null as Course | null,
    currentCourseAssignments: [] as Assignment[],
    isLoading: false,
    isAssignmentListLoading: false,
  }),
  actions: {
    async listAll(): Promise<void> {
      this.isLoading = true;
      try {
        this.list = await getCourses();
      } finally {
        this.isLoading = false;
      }
    },

    selectCourse(id: number): void {
      const found = this.list.find((item) => item.id === id);
      this.currentCourse = found ?? null;
    },

    async listAllAssignmentsOfCurrentCourse(): Promise<void> {
      this.isAssignmentListLoading = true;
      try {
        if (this.currentCourse != undefined)
          this.currentCourseAssignments = await getAssignmentByCourseId(
            this.currentCourse.id
          );
      } finally {
        this.isAssignmentListLoading = false;
      }
    },
  },
});
