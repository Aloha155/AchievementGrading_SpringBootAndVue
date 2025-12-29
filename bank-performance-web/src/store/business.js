import Vue from "vue"
import Vuex from "vuex"
import httpUtil from "../assets/script/util/http"

Vue.use(Vuex)

export default {
    namespaced: true,
    state: {},
    mutations: {},
    actions: {
        async ARoleListAll(context, params) {
            return httpUtil.get("/business/listAll", params)
        },
        async AUpdateRole(context, params) {
            return httpUtil.get("/business/updateRole", params)
        },
        async AGet(context, params) {
            return httpUtil.get("/business/get", params)
        },
        async ACreate(context, params) {
            return httpUtil.post("/business/create", params, "string")
        },
        async AListAll(context, params) {
            return httpUtil.get("/business/listAll", params, "string")
        },
        async APaging(context, params) {
            return httpUtil.get("/business/paging", params)
        },
        async AUpdate(context, params) {
            return httpUtil.post("/business/update", params, "string")
        },
        async AUpdateState(context, params) {
            return httpUtil.post("/business/updateState", params, "string")
        },
        async ARemove(context, params) {
            return httpUtil.post("/business/remove", params, "string")
        },
    }
}
