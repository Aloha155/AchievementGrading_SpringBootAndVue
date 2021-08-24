<template>
    <el-dialog title="办理业务" :visible="dialogFormVisible" :show-close="false">
        <el-form>
            <el-form-item label="业务编号" prop="name" :label-width="formLabelWidth">
                <el-input v-model="id" :disabled="true" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务名称" prop="name" :label-width="formLabelWidth">
                <el-input v-model="businessName" :disabled="true" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="办理人姓名" prop="code" :label-width="formLabelWidth">
                <el-input v-model="name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="办理人电话" prop="businessStatus" :label-width="formLabelWidth">
                <el-input v-model="phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="department" :label-width="formLabelWidth">
                <el-input v-model="remake" autocomplete="off"></el-input>
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
import message from "../../../assets/script/util/message";


export default {
    name: "TransactionBusinessDialog",
    props: {
        dialogFormVisible: Boolean,
        disabled: Boolean,
        id: Number
    },
    data() {
        return {
            phone: '',
            name: '',
            businessName: '',
            remake: '',
            formLabelWidth: '120px'
        }
    },
    watch: {
        dialogFormVisible() {
            this.init()
        }
    },
    methods: {
        ...mapActions("userBusiness", ["ACreate"]),
        ...mapActions("business", ["AGet"]),
        async init() {
            const result = await this.AGet({"id": this.id})
            console.log(result)
            if (result.status === 200) {
                const business = result.data
                this.businessName = business.name
            }
        },
        async save() {
            if (this.name === undefined || this.phone === undefined) {
                message.messageErrorAlert("办理人信息不能为空")
                return false
            }
            const date = {
                "username": this.name,
                "phone": this.phone,
                "businessId": this.id,
                "remake": this.remake
            }
            const result = await this.ACreate(date)
            if (result.status === 200) {
                message.messageSuccessAlert("业务办理成功，等待业务员确认结束")
                this.name = ''
                this.phone = ''
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