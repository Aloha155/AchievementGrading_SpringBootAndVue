<template>
    <el-header class="header-box">
        <ul class="left-box">
            <li>
                <button
                    :class="collapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
                    @click="MUpdateCollapse"
                ></button>
            </li>
        </ul>
        <ul class="right-box">
            <li>
                {{ user.role }}
            </li>
            <li v-if="user.role !== '管理员'">
                {{ user.departmentName }}
            </li>
            <li>
                {{ user.name }}
            </li>
            <li>
                <button class="el-icon-setting" title="设置" v-popover:setting></button>
                <el-popover ref="setting" width="200" trigger="click">
                    <div @click="updatePassword" class="setting-item">
                        修改密码
                    </div>
                    <div @click="logout" class="setting-item">
                        退出
                    </div>
                </el-popover>
            </li>
        </ul>
        <UpdatePasswordDialog
            :dialogFormVisible.sync="dialogFormVisible">
        </UpdatePasswordDialog>
    </el-header>

</template>

<script>
import {mapState, mapMutations, mapActions} from "vuex"
import Cookies from "js-cookie"

export default {
    components: {
        UpdatePasswordDialog: () => import("../global/UpdatePasswordDialog"),
    },
    data() {
        return {
            dialogFormVisible: false
        }
    },
    componentName: "header",
    computed: {
        ...mapState("user", ["user"]),
        ...mapState("menu", ["collapse"])
    },
    methods: {
        ...mapMutations("menu", ["MUpdateCollapse"]),
        ...mapActions("user", ["AUpdatePassword"]),
        async logout() {
            this.$confirm("确定退出？", "提示", {
                type: "warning"
            }).then(() => {
                Cookies.remove("token");
                this.$router.replace(`/login`)
            })
        },
        async updatePassword() {
            this.dialogFormVisible = true
        }
    }
}
</script>

<style type="text/less" lang="less" scoped>
@import "../../assets/style/color";
@import "../../assets/style/config";
@import "../../assets/style/mixin";

.header-box {
    background-color: @theme-color;
    height: @herder-height;
    display: flex;
    align-items: center;
    color: #fff;
    padding: 0 25px;

    .left-box {
        flex: 1;
        font-size: @big-font-size + 2px;
    }

    .right-box {
        display: flex;

        li {
            padding: 0 10px;
            font-size: @medium-font-size;

            button {
                font-size: @big-font-size + 2px;
            }
        }
    }
}

.setting-item {
    font-size: 16px;
    line-height: 35px;
    cursor: pointer;
}
</style>
