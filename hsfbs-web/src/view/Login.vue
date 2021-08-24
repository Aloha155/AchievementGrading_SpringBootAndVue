<template style="color: white">
    <div class="login-page">
        <el-row type="flex" class="row-bg" justify="space-around">
            <el-col :span="6">
                <el-card class="card">
                    <div slot="header" style="text-align: center">
                        <span>X银行地方支行绩效考核系统</span>
                    </div>
                    <el-form :model="loginForm" :rules="loginRules" class="login-form">
                        <el-form-item prop="phone">
                            <el-input v-model="loginForm.phone" placeholder="请输入账号" autocomplete="on">
                                <template slot="prepend">
                                    <i class="el-icon-user"></i>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"
                                      autocomplete="on">
                                <template slot="prepend">
                                    <i class="el-icon-unlock"></i>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm()" style="width:100%;">登录</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import tips from "@/assets/script/util/tips";
import {mapActions} from "vuex"
import {LoginForm} from "@/assets/script/model";

export default {
    name: "Login",
    data() {
        return {
            loginForm: new LoginForm(),
            loginRules: {
                phone: [
                    {required: true, message: '请输入账号', trigger: 'blur'},
                    {max: 11, message: '输入字符超出限制，请重试', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {max: 16, message: '输入字符超出限制，请重试', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        ...mapActions("user", ["ALogin"]),
        async submitForm() {
            const phone = this.loginForm.phone;
            const password = this.loginForm.password;
            if (phone === undefined || password === undefined) {
                await tips.warn("账号密码不能为空");
                return false;
            }
            if (!(/^[0-9a-zA-Z]+$/.test(phone))) {
                await tips.error("账号格式错误，请重新输入");
                return false;
            }
            if (!(/^[0-9a-zA-Z]+$/.test(password))) {
                await tips.error("密码格式错误");
                return false;
            }
            try {
                await this.ALogin(this.loginForm)
            } catch (e) {
                throw new Error(e)
            }
        }
    }
}
</script>

<style scoped >


.login-page {
    height: 100vh;
    width: 100%;
    background-image: url("../assets/script/img/bj.jpg");
    background-size: 100% 100%;
    background-repeat: repeat;
}

.login-form {


}

.card {
    width: 100%
}

.row-bg {
    padding: 20% 0;
    float: none;
}

</style>