import { defineStore } from "pinia";
import { getUser } from "@/services/userService";
import type { User } from "@/types/user";

export const useUserStore = defineStore("user", {
  state: () => ({
    currentUser: {} as User,
    isLoading: false,
    error: null as string | null,
  }),

  actions: {
    async getUser(id: number) {
      this.isLoading = true;
      this.error = null;
      try {
        this.currentUser = await getUser(id);
      } catch (error) {
        this.error = "Failed to load user data";
        console.error(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
});
