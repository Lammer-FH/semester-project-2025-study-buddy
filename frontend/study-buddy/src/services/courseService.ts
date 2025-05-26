import { Assignment } from '@/types/assignment'
import { Course } from '@/types/course'
import axios from 'axios'

export async function getCourses(): Promise<Course[]> {
  try {
    const response = await axios.get<Course[]>('/courses')
    return response.data
  } catch (error) {
    console.warn('API /courses failed, returning fallback data.')
    console.error(error)

    // Fallback-Daten
    return [
      { id: 1, title: 'Fallback Course 1', description: 'Some Description' },
      { id: 2, title: 'Fallback Course 2', description: 'Some Description' },
      { id: 3, title: 'Fallback Course 3', description: 'Some Description' }
    ]
  }
}

export async function getAssignmentByCourseId(id: number): Promise<Assignment[]> {
  try {
    const response = await axios.get<Assignment[]>('courses/{id}/assignments', {
      params: { sort: 'date' }
    })
    return response.data
  } catch (error) {
    console.warn('API /assignments failed, returning fallback data.')
    console.error(error)

    // Fallback-Daten
    return [
      { id: 1, title: 'Fallback Course Assignment 1', date: '2025-06-01' },
      { id: 2, title: 'Fallback Course Assignment 2', date: '2025-06-05' },
      { id: 3, title: 'Fallback Course Assignment 3', date: '2025-06-05' }
    ]
  }
}