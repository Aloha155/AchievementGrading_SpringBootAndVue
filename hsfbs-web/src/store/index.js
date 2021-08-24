import Vue from "vue"
import Vuex from "vuex"
import user from "./user"
import menu from "./menu";
import series from "./series";
import performance from "./performance";
import business from "./business";
import role from "./role";
import department from "./department";
import userBusiness from "./userBusiness";


Vue.use(Vuex)

export default new Vuex.Store({
    state: {},
    mutations: {},
    actions: {},
    modules: {
        userBusiness,
        performance,
        business,
        role,
        user,
        menu,
        department,
        series
    }
})
