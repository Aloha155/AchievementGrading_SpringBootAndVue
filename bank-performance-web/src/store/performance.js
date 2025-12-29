import Vue from "vue"
import Vuex from "vuex"
import httpUtil from "../assets/script/util/http"

Vue.use(Vuex)

export default {
    namespaced: true,
    state: {},
    mutations: {},
    actions: {
        async APaging(context, params) {
            let _params = Object.assign({}, params);
            _params.page--;
            return httpUtil.get("/performance/paging", _params)
        },
        async APagingUserById(context, params) {
            let _params = Object.assign({}, params);
            _params.page--;
            return httpUtil.get("/performance/pagingUserById", _params)
        },
        async AListAll(context, params) {
            return httpUtil.get("/performance/listAll", params)
        },
        async ARemove(context, params) {
            return httpUtil.post("/performance/remove", params)
        },
        async ACreate(context, params) {
            return httpUtil.post("/performance/create", params,"string")
        },
        async AUpdate(context, params) {
            return httpUtil.post("/performance/updateName", params)
        },
        async AGet(context, params) {
            return httpUtil.get("/performance/get", params)
        }
    }
}
