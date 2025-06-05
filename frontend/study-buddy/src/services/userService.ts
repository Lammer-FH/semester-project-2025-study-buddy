import { User } from "@/types/user";

import api from "./api";

const fallbackUser: User = {
  id: 1,
  name: "Eric Cartman",
  email: "cartman@southpark.edu",
  program: "Authoritah Studies",
  semester: 4,
  studentNumber: 1069666,
};

export async function getUser(id: number): Promise<User> {
  try {
    const response = await api.get<User>(`/users/${id}`);
    return response.data;
  } catch (error) {
    console.error("Failed to fetch user, returning fallback data", error);
    return fallbackUser;
  }
}
