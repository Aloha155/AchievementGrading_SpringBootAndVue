<template>
    <el-dialog title="修改业务信息" :visible="dialogFormVisible" :show-close="false">
        <el-form>
            <el-form-item label="业务名" prop="name" :label-width="formLabelWidth">
                <el-input v-model="name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务编码" prop="code" :label-width="formLabelWidth">
                <el-input v-model="code" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
import {businessStateConstant} from "@/assets/script/constant";
import {mapActions} from "vuex"
import tips from "@/assets/script/util/tips";
import message from "../../../assets/script/util/message";


export default {
    name: "UpdateBusinessDialog",
    props: {
        dialogFormVisible: Boolean,
        disabled: Boolean,
        id: Number
    },
    data() {
        return {
            name: '',
            code: '',
            businessStateList: businessStateConstant(),
            formLabelWidth: '120px'
        }
    },
    watch: {
        dialogFormVisible() {
            this.init()
        }
    },
    methods: {
        ...mapActions("business", ["ACreate", "AGet", "AUpdate"]),
        ...mapActions("department", ["AListAll"]),
        async init() {
            const businessResult = await this.AGet({"id": this.id});
            if (businessResult.status === 200) {
                const business = businessResult.data
                this.name = business.name
                this.code = business.code
            } else {
                await tips.error(businessResult.message);
            }
        },
        async save() {
            if (this.name === undefined || this.code === undefined) {
                message.messageErrorAlert("参数不能为空")
                return false
            }
            const date = {
                "id": this.id,
                "name": this.name,
                "code": this.code,
            }
            const result = await this.AUpdate(date)
            if (result.status === 200) {
                message.messageSuccessAlert("修改成功，业务一旦创建则无法删除")
                this.name = ''
                this.code = ''
                this.businessState = "PASS"
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