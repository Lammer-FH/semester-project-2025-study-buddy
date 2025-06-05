import { Assignment } from "@/types/assignment";
import api from "./api";

export async function getAssignments(): Promise<Assignment[]> {
  try {
    const response = await api.get<Assignment[]>("/assignments", {
      params: { sort: "date" },
    });
    return response.data;
  } catch (error) {
    console.warn("API /assignments failed, returning fallback data.");
    console.error(error);

    // Fallback-Daten
    return [
      { id: 1, title: "Fallback Assignment 1", deadline: "2025-06-01" },
      { id: 2, title: "Fallback Assignment 2", deadline: "2025-06-05" },
      { id: 3, title: "Fallback Assignment 2", deadline: "2025-06-05" },
    ];
  }
}
