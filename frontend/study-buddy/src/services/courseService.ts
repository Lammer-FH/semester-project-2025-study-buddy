import { Assignment } from "@/types/assignment";
import { Course } from "@/types/course";
import api from "./api";

export async function getCourses(): Promise<Course[]> {
  const response = await api.get<Course[]>("/courses");
  return response.data;
}

export async function getAssignmentsByCourseId(
  id: number
): Promise<Assignment[]> {
  const response = await api.get<Assignment[]>(`/courses/${id}/assignments`, {
    params: { sort: "date" },
  });
  return response.data;
}

export async function createCourse(
  course: Omit<Course, "id">
): Promise<Course> {
  const response = await api.post<Course>("/courses", course);
  return response.data;
}

export async function updateCourse(
  id: number,
  course: Partial<Course>
): Promise<Course> {
  const response = await api.put<Course>(`/courses/${id}`, course);
  return response.data;
}

export async function deleteCourse(id: number): Promise<void> {
  await api.delete(`/courses/${id}`);
}
