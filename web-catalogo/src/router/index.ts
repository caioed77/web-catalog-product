import Dashboard from "../views/Dashboard.vue";
import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/",
    component: Dashboard,
  },
  {
    path: "/dashboard",
    component: Dashboard,
    children: [
      {
        path: "home",
        component: () => import("../views/Home.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
