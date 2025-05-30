import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '../views/TabsPage.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/tabs/assignment-list'
  },
  {
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/tab1'
      },
      {
        path: 'assignment-list',
        component: () => import('@/views/AssignmentListPage.vue')
      },
      {
        path: 'course-list',
        component: () => import('@/views/CourseListPage.vue')
      },
      {
        path: 'course/:id',
        component: ()=> import('../views/CoursePage.vue')
      },
      {
        path: 'user',
        component: () => import('@/views/UserPage.vue')
      },
      {
        path: 'info',
        component: () => import('@/views/InfoPage.vue')
      }
    ]
  }  
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
