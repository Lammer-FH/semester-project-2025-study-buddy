import { Assignment } from "@/types/assignment";
import api from "./api";

// Get all assignments globally, sorted by date
export async function getAssignments(): Promise<Assignment[]> {
  const response = await api.get<Assignment[]>("/assignments", {
    params: { sort: "date" },
  });
  return response.data;
}

// Get assignments for a specific course
export async function getAssignmentsByCourse(
  courseId: number
): Promise<Assignment[]> {
  const response = await api.get<Assignment[]>(
    `/courses/${courseId}/assignments`,
    {
      params: { sort: "date" },
    }
  );
  if (response.status === 204) {
    return []; // No assignments
  }
  return response.data;
}

// Get a single assignment by its ID
export async function getAssignmentById(id: number): Promise<Assignment> {
  const response = await api.get<Assignment>(`/assignments/${id}`);
  return response.data;
}

// Create a new assignment for a course
export async function createAssignment(
  courseId: number,
  assignment: Omit<Assignment, "id">
): Promise<Assignment> {
  const response = await api.post<Assignment>(
    `/courses/${courseId}/assignments`,
    assignment
  );
  return response.data;
}

// Update an assignment by ID
export async function updateAssignment(
  id: number,
  assignment: Omit<Assignment, "id">
): Promise<Assignment> {
  const response = await api.put<Assignment>(`/assignments/${id}`, assignment);
  return response.data;
}

// Delete an assignment by ID
export async function deleteAssignment(id: number): Promise<void> {
  await api.delete(`/assignments/${id}`);
}
