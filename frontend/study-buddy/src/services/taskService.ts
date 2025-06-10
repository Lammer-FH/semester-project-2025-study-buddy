import api from "./api";
import type { Task } from "@/types/task";
// Get all tasks for a specific assignment
export async function getTasksByAssignmentId(
  assignmentId: number
): Promise<Task[]> {
  const response = await api.get<Task[]>(`/assignments/${assignmentId}/tasks`);
  return response.status === 204 ? [] : response.data;
}

// Add a new task to an assignment
export async function addTaskToAssignment(
  assignmentId: number,
  task: Omit<Task, "id">
): Promise<Task> {
  const response = await api.post<Task>(
    `/assignments/${assignmentId}/tasks`,
    task
  );
  return response.data;
}

// Update an existing task
export async function updateTask(
  taskId: number,
  task: Omit<Task, "id">
): Promise<Task> {
  console.log("updating task with id ", taskId, task);
  const response = await api.put<Task>(`/tasks/${taskId}`, task);
  return response.data;
}

// Delete a task
export async function deleteTask(taskId: number): Promise<void> {
  await api.delete(`/tasks/${taskId}`);
}
