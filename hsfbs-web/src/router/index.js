import Vue from "vue"
import VueRouter from "vue-router"
import Cookies from "js-cookie"
import department from "../view/department/script/router";
import performance from "../view/performance/script/router";
import business from "../view/business/script/router"
import user from "../view/user/script/router";
import admin from "../view/admin/script/router";
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "content",
        redirect: "/department",
        component: () => import("../view/Content"),
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
        component: () => import("../view/Login")
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
