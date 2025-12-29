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
            return httpUtil.get("/department/paging", _params)
        },
        async APagingUserById(context, params) {
            let _params = Object.assign({}, params);
            _params.page--;
            return httpUtil.get("/department/pagingUserById", _params)
        },
        async AListAll(context, params) {
            return httpUtil.get("/department/listAll", params)
        },
        async ARemove(context, params) {
            return httpUtil.post("/department/remove", params,"string")
        },
        async ARemoveImage(context, params) {
            return httpUtil.post("/department/removeImage", params)
        },
        async AUpdateUserDepartment(context, params) {
            return httpUtil.post("/department/updateUserDepartment", params,"string")
        },
        async ASave(context, params) {
            return httpUtil.post("/department/save", params)
        },
        async ACreate(context, params) {
            return httpUtil.post("/department/create", params,"string")
        },
        async AUpdate(context, params) {
            return httpUtil.post("/department/updateName", params)
        },
        async AUpdateName(context, params) {
            return httpUtil.post("/department/updateName", params,"string")
        },
        async AGet(context, params) {
            return httpUtil.get("/department/get", params)
        }
    }
}
