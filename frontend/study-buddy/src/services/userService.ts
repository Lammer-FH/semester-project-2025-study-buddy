import { Assignment } from "@/types/assignment";
import { User } from "@/types/user";

import api from "./api";

export async function getUser(id: number): Promise<User> {
  try {
    const response = await api.get<User>(`/users/${id}/`, {
      
    });
    return response.data;
  } catch (error) {
    console.warn("API /assignments failed, returning fallback data.");
    console.error(error);

    // Fallback-Daten
    return { id: 1,  name: "viki", email: "aa", program: "aa", semester: 2, studentNumber:123 };
  }
}
