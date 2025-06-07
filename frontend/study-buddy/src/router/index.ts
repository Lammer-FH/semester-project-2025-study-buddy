import { createRouter, createWebHistory } from "@ionic/vue-router";
import { RouteRecordRaw } from "vue-router";
import TabsPage from "@/views/TabsPage.vue";
import AssignmentListPage from "@/views/AssignmentListPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/tabs/assignment-list",
  },
  {
    path: "/tabs/",
    component: TabsPage,
    children: [
      {
        path: "",
        redirect: "/tabs/assignment-list",
      },
      {
        path: "assignment-list",
        component: AssignmentListPage,
      },
      {
        path: "course-list",
        component: () => import("@/views/CourseListPage.vue"),
      },
      {
        path: "course/:id",
        component: () => import("../views/CoursePage.vue"),
      },
      {
        path: "user",
        component: () => import("@/views/UserPage.vue"),
      },
      {
        path: "info",
        component: () => import("@/views/InfoPage.vue"),
      },
      {
        path: "course/new",
        component: () => import("@/views/CourseCreatePage.vue"),
      },
      {
        path: "course/:id/edit",
        component: () => import("@/views/CourseEditPage.vue"),
      },
      {
        path: "assignment/new",
        component: () => import("@/views/AssignmentCreatePage.vue"),
      },
      {
        path: "assignment/:id/edit",
        component: () => import("@/views/AssignmentEditPage.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
