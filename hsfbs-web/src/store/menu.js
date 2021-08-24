import Vue from "vue"
import Vuex from "vuex"
Vue.use(Vuex);

export default {
    namespaced: true,
    state: {
        collapse: false
    },
    mutations: {
        MUpdateCollapse(state) {
            state.collapse = !state.collapse
        }
    },
    actions: {}
}
