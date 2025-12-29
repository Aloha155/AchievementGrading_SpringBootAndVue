import Vue from "vue"
import Vuex from "vuex"
import httpUtil from "../assets/script/util/http"

Vue.use(Vuex)

export default {
    namespaced: true,
    state: {},
    mutations: {},
    actions: {
        async AUserBusinessGet(context, params) {
            return httpUtil.get("/userBusiness/get", params)
        },
        async ACreate(context, params) {
            return httpUtil.post("/userBusiness/create", params, "string")
        },
        async AHisPaging(context, params) {
            return httpUtil.post("/userBusiness/paging", params)
        },
        async AUpdate(context, params) {
            return httpUtil.post("/userBusiness/update", params, "string")
        },
        async AUpdateUserBusinessState(context, params) {
            return httpUtil.post("/userBusiness/updateState", params, "string")
        },
        async ACreateAudit(context, params) {
            return httpUtil.post("/userBusiness/createAudit", params, "string")
        },
        async AAudit(context, params) {
            return httpUtil.post("/userBusiness/audit", params, "string")
        }
    }
}
