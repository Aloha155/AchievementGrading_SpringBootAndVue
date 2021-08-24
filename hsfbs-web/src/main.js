import Vue from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import "./assets/style/index.less"
import "./assets/style/el.less"
import config from "./assets/script/constant/config"

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$config = config;
import filters from "./assets/script/filter"

Vue.prototype.$filter = filters;
Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key])
});

Vue.component("column-assist", () => import("./components/global/ColumnAssist"))

Vue.component("pagination-assist", () =>
    import("./components/global/PaginationAssist")
);

Vue.prototype.$resultNotify = function(result) {
  return new Promise((resolve, reject) => {
    if (result?.status !== 200) {
      window.app.$notify({ message: result?.message || "服务器错误" });
      reject(result)
    } else {
      resolve(result)
    }
  })
};

window.app = new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount("#app");
