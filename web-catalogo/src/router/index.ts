import { createRouter, createWebHashHistory } from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import { useUsuarioStore } from "../store/UsuarioStore";

const routes = [
  {
    path: "/",
    component: Home,
  },
  {
    path: "/home",
    component: Home,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/cadastro",
    component: Register,
  },
  {
    path: "/dashboard",
    component: Dashboard,
    children: [
      {
        path: "produtos",
        component: () => import("../views/PageProdutos.vue"),
      },
      
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to) => {
  const usuarioStore = useUsuarioStore()
  if (to.path === '/login') {
    return true
  } else {
    if (usuarioStore.usuario.accessToken !== '') {
      return true
    }
    return '/login'
  }
})

export default router;
