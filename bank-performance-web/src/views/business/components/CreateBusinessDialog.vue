<template>
    <el-dialog title="新增可办业务" :visible="dialogFormVisible" :show-close="false">
        <el-form>
            <el-form-item label="业务名" prop="name" :label-width="formLabelWidth">
                <el-input v-model="name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务编码" prop="code" :label-width="formLabelWidth">
                <el-input v-model="code" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="业务状态" prop="businessStatus" :label-width="formLabelWidth">
                <el-select v-model="businessState" filterable placeholder="请选择所有业务的状态">
                    <el-option
                        v-for="item in businessStateList"
                        :key="item.key"
                        :label="item.name"
                        :value="item.key">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="属于部门" prop="department" :label-width="formLabelWidth">
                <el-select v-model="departmentId" filterable placeholder="请选择所有业务的状态">
                    <el-option
                        v-for="item in departmentList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
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
    name: "CreateBusinessDialog",
    props: {
        dialogFormVisible: Boolean,
        disabled: Boolean,
    },
    data() {
        return {
            name: '',
            code: '',
            businessState: '',
            departmentId: '',
            departmentList: [],
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
        ...mapActions("business", ["ACreate"]),
        ...mapActions("department", ["AListAll"]),
        async init() {
            this.businessState = "PASS"
            const departmentResult = await this.AListAll();
            if (departmentResult.status === 200) {
                this.departmentList = departmentResult.data
            } else {
                await tips.error(departmentResult.message);
            }
        },
        async save() {
            if (this.name === undefined || this.code === undefined) {
                message.messageErrorAlert("评分不能为空")
                return false
            }
            const date = {
                "name": this.name,
                "code": this.code,
                "businessState": this.businessState,
                "departmentId": this.departmentId
            }
            const result = await this.ACreate(date)
            if (result.status === 200) {
                message.messageSuccessAlert("新增成功，业务一旦创建则无法删除")
                this.name = ''
                this.code = ''
                this.businessState = "PASS"
                await this.close()
            } else {
                message.messageErrorAlert(result.message)
            }
        },
        async close() {
            this.$router.go(0)
            await this.$emit("update:dialogFormVisible", false);
        }
    }
}
</script>

<style scoped>

</style>