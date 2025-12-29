<template>
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" :show-close="false" :modal="false">
        <el-form :model="updatePasswordDto">
            <el-form-item label="请输入旧密码" :label-width="formLabelWidth">
                <el-input v-model="updatePasswordDto.oldPassword" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="请输入新密码" :label-width="formLabelWidth">
                <el-input v-model="updatePasswordDto.newOnePassword" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="请再次输入新密码" :label-width="formLabelWidth">
                <el-input v-model="updatePasswordDto.newTwoPassword" type="password" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>

import {mapActions} from "vuex"
import message from "../../assets/script/util/message";
import Cookies from "js-cookie"

export default {
    name: "UpdatePasswordDialog",
    props: {
        dialogFormVisible: Boolean,
    },
    data() {
        return {
            formLabelWidth: '150px',
            updatePasswordDto: {
                newOnePassword: '',
                newTwoPassword: '',
                oldPassword: ''
            }
        }
    },
    watch: {
        dialogFormVisible() {
            this.init();
        }
    },
    methods: {
        ...mapActions("user", ["AUpdatePassword"]),
        async init() {

        },
        async save() {
            if (this.updatePasswordDto.oldPassword === undefined || this.updatePasswordDto.oldPassword === null) {
                message.messageWarnAlert("旧密码不能为空");
                return false
            }
            if (this.updatePasswordDto.newOnePassword === undefined || this.updatePasswordDto.newOnePassword === null) {
                message.messageWarnAlert("新密码不能为空")
                return false
            }
            if (this.updatePasswordDto.newTwoPassword === undefined || this.updatePasswordDto.newTwoPassword === null) {
                message.messageWarnAlert("确认密码项不能为空")
                return false
            }
            if (this.updatePasswordDto.newOnePassword !== this.updatePasswordDto.newTwoPassword) {
                message.messageWarnAlert("两次新密码不一致")
                return false
            }

            const result = await this.AUpdatePassword({
                "newPassword": this.updatePasswordDto.newTwoPassword,
                "oldPassword": this.updatePasswordDto.oldPassword
            })
            if (result.status === 200) {
                message.messageSuccessAlert("密码修改成功");
                Cookies.remove("token");
                await this.close()
                await this.$router.replace(`/login`)
            } else {
                message.messageWarnAlert(result.message);
                await this.close()
            }
        },
        async close() {
            await this.$emit("update:dialogFormVisible", false);
        }
    }
}
</script>

<style scoped>

</style>