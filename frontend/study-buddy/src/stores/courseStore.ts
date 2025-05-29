import { defineStore } from 'pinia'
import type { Course } from '@/types/course'
import { getCourses } from '@/services/courseService' 
export const useAssignmentStore = defineStore('course', {
  state: () => ({
    list: [] as Course[],
    currentCourse: null as Course | null,
    isLoading: false
  }),
  actions: {
    async listAll(): Promise<void> {
      this.isLoading = true
      try {
        this.list = await getCourses()
      } finally {
        this.isLoading = false
      }
    },

    selectCourse(id: number): void {
      const found = this.list.find(item => item.id === id)
      this.currentCourse = found ?? null
    }
  }
})
