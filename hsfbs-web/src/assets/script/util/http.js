/**
 * http请求的工具类
 *
 * @author yj
 */
import Cookies from "js-cookie"
import axios from "axios"
import {Result} from "../model"
import config from "../constant/config"
import Qs from 'qs'
import jsCookie from "js-cookie"

axios.defaults.baseURL = config.host
// axios.defaults.baseURL = "/api"
export default {
    /**
     * get 请求
     * @param {string} url
     * @param {*?} params
     * @returns {Promise<>}
     */
    async get(url, params) {
        let result = null;
        try {
            const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${jsCookie.get("token")}`
                },
                params,
                paramsSerializer: (params) => {
                    return Qs.stringify(params, {
                        arrayFormat: "repeat"
                    })
                }
            })
            this._handleToken(response);
            result = response.data
        } catch (e) {
            result = await new Result(500, e, "服务器错误")
        }
        return result
    },

    /**
     * post 请求
     * @param {string} url
     * @param body
     * @param type
     * @returns {Promise<>}
     */
    async post(url, body, type = "json") {
        let result = null;
        try {
            const response = await axios.post(
                url,
                type === "json" ? body : Qs.stringify(body),
                {
                    headers: {
                        Authorization: `Bearer ${jsCookie.get("token")}`
                    }
                }
            );
            this._handleToken(response);
            result = response.data
        } catch (e) {
            result = await new Result(500, e, "服务器错误")
        }
        return result
    },

    _handleToken(response = {}) {
        const headers = response.headers || {};
        if (headers.token) {
            Cookies.set("token", headers.token, {expires: 30})
        }
        if (response.data.status === 401) {
            Cookies.remove("token");
            window.app.$router.replace(`/login`)
        }
    }
}
