import Vue from "vue"
import Vuex from "vuex"
import httpUtil from "../assets/script/util/http"

Vue.use(Vuex);

export default {
    namespaced: true,
    state: {},
    mutations: {
    },
    actions: {
        async APaging(context, params) {
            let _params = Object.assign({}, params);
            _params.page--;
            return httpUtil.get("/series/paging", _params);
        },
        async AListAll(context, params) {
            return httpUtil.get("/series/listAll", params);
        },
        async AGetByNumber(context, params){
            return httpUtil.get("/series/getByNumber", params);
        }
    }
}
