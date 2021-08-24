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
            return httpUtil.get("/role/listAll", params)
        },
        async AUpdateRole(context, params){
            return httpUtil.get("/role/updateRole", params)
        },
        async AGet(context, params) {
            return httpUtil.get("/role/get", params)
        }
    }
}
