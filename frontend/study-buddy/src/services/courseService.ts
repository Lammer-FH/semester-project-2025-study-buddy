import { Assignment } from "@/types/assignment";
import { Course } from "@/types/course";
// import axios from 'axios'
import api from "./api";

export async function getCourses(): Promise<Course[]> {
  const response = await api.get<Course[]>("/courses");
  return response.data;
}

export async function getAssignmentByCourseId(
  id: number
): Promise<Assignment[]> {
  const response = await api.get<Assignment[]>(`courses/${id}/assignments`, {
    params: { sort: "date" },
  });
  return response.data;
}
