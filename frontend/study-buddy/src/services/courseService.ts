import axios from 'axios'

export interface Course {
  id: number
  title: string
  description: string
}

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
      { id: 3, title: 'Fallback Course 2', description: 'Some Description' }
    ]
  }
}