import { defineStore } from 'pinia'
import type { Assignment } from '@/types/assignment'
import { getAssignments } from '@/services/assignmentService' 
export const useAssignmentStore = defineStore('assignment', {
  state: () => ({
    list: [] as Assignment[],
    currentAssignment: null as Assignment | null,
    isLoading: false
  }),
  actions: {
    async listAll(): Promise<void> {
      this.isLoading = true
      try {
        this.list = await getAssignments()
      } finally {
        this.isLoading = false
      }
    },

    getAssignment(id: number): void {
      const found = this.list.find(item => item.id === id)
      this.currentAssignment = found ?? null
    }
  }
})
