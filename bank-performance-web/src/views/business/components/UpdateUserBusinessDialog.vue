<template>
    <el-dialog title="修改当前业务信息" :visible="dialogFormVisible" :show-close="false">
        <el-form>
            <el-form-item label="修改意见" prop="name" :label-width="formLabelWidth">
                <el-input v-model="userBusiness.auditCause" :disabled="true" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务办理人姓名" prop="name" :label-width="formLabelWidth">
                <el-input v-model="userBusiness.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务办理人电话" prop="code" :label-width="formLabelWidth">
                <el-input v-model="userBusiness.phone" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
import {UserBusiness} from "@/assets/script/model";
import {mapActions} from "vuex"
import tips from "@/assets/script/util/tips";
import message from "../../../assets/script/util/message";


export default {
    name: "UpdateUserBusinessDialog",
    props: {
        dialogFormVisible: Boolean,
        disabled: Boolean,
        id: Number
    },
    data() {
        return {
            userBusiness: new UserBusiness(),
            formLabelWidth: '120px'
        }
    },
    watch: {
        dialogFormVisible() {
            this.init()
        }
    },
    methods: {
        ...mapActions("userBusiness", ["ACreate", "AUserBusinessGet", "AUpdate"]),
        ...mapActions("department", ["AListAll"]),
        async init() {
            const businessResult = await this.AUserBusinessGet({"id": this.id});
            if (businessResult.status === 200) {
                this.userBusiness = businessResult.data
            } else {
                await tips.error(businessResult.message);
            }
        },
        async save() {
            if (this.userBusiness.username === undefined || this.userBusiness.phone === undefined) {
                message.messageErrorAlert("参数不能为空")
                return false
            }
            const date = {
                "id": this.id,
                "username": this.userBusiness.username,
                "phone": this.userBusiness.phone,
            }
            const result = await this.AUpdate(date)
            if (result.status === 200) {
                message.messageSuccessAlert("修改成功，通知业务员去完成")
                this.userBusiness = new UserBusiness()
                await this.close()
            } else {
                message.messageErrorAlert(result.message)
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