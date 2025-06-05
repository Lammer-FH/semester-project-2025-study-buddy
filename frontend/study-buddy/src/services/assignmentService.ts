import { Assignment } from "@/types/assignment";
import api from "./api";

export async function getAssignments(): Promise<Assignment[]> {
  const response = await api.get<Assignment[]>("/assignments", {
    params: { sort: "date" },
  });
  return response.data;
}
