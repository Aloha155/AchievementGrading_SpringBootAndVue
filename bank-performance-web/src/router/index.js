import Vue from "vue"
import VueRouter from "vue-router"
import Cookies from "js-cookie"
import department from "../views/department/script/router";
import performance from "../views/performance/script/router";
import business from "../views/business/script/router"
import user from "../views/user/script/router";
import admin from "../views/admin/script/router";
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "content",
        redirect: "/department",
        component: () => import("../views/Content"),
        children: [
            ...department,
            ...user,
            ...performance,
            ...business,
            ...admin
        ]
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../views/Login")
    }
];

const router = new VueRouter({
    mode: "hash",
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, from, next) => {
    if (!Cookies.get("token") && to.path !== "/login") {
        next("/login");
        return
    }
    next() // 必须使用 next ,执行效果依赖 next 方法的调用参数
})

export default router
