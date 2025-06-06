import { defineStore } from "pinia";
import { getUser } from "@/services/userService";
import type { User } from "@/types/user";
import { PersistenceOptions } from "pinia-plugin-persistedstate";

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

  persist: {
    key: "user-store",
    storage: localStorage,
    // Only persist user data, not loading/error states
    paths: ["currentUser"],
  } as PersistenceOptions,
});
