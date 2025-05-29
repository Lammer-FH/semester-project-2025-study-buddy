import { defineStore } from "pinia";
import api from "@/services/api";

import type { User } from "@/types/user";

export const useUserStore = defineStore("user", {
  state: () => ({
    currentUser: {} as User,
    isLoading: false,
  }),
  actions: {
    async getUser(id: number): Promise<void> {
      this.isLoading = true;
      try {
        const response = await api.get<User>(`/users/${id}`);
        this.currentUser = response.data;
        console.log("In try of UserStore");
      } catch (error) {
        console.warn(`API /users/${id} failed.`);
        console.error(error);
        this.currentUser = {
          id: 1,
          name: "FallbackUser",
          email: "fallbacl@technikum.wien",
          program: "MSE",
          semester: 2,
          studentNumber: 7216817,
        };

      } finally {
        this.isLoading = false;
        console.log("In finally");
      }
    },
  },
});
