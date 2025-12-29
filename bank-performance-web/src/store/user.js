import Vue from "vue"
import Vuex from "vuex"
import httpUtil from "../assets/script/util/http"
import tips from "@/assets/script/util/tips";

Vue.use(Vuex);

export default {
    namespaced: true,
    state: {
        user: null
    },
    mutations: {
        UserInfo(state, info) {
            state.user = info
        }
    },
    actions: {
        async ALogin(context, params) {
            const result = await httpUtil.post("/user/login", params, "string");
            if (result.status === 200) {
                await tips.success("登录成功");
                await window.app.$router.replace("/")
            } else {
                await tips.error(result.message)
            }
        },
        async AGet(context, params) {
            const result = await httpUtil.get("/user/get", params);
            if (result.status === 200) {
                context.commit("UserInfo", result.data);
                return result.data
            } else {
                await tips.error(result.message)
            }
        },
        async AUserGet(context, params) {
            return httpUtil.get("/user/getByUserId", params);
        },
        async AUpdatePassword(context, params) {
            return httpUtil.post("/user/updatePassword", params);
        },
        async ACreate(context, params) {
            return httpUtil.post("/user/create", params);
        },
        async AUpdate(context, params) {
            return httpUtil.post("/user/update", params);
        },
        async ARemove(context, params) {
            return httpUtil.post("/user/remove", params, "string");
        },
    }
}
