<template>
    <el-dialog title="新增人员" :visible="dialogFormVisible" :show-close="false">
        <el-form :model="user">
            <el-form-item label="姓名" prop="name" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '参数不能为空'}]">
                <el-input v-model="user.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '参数不能为空'}]">
                <el-input v-model="user.password" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="phone" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '电话不能为空'}]">
                <el-input v-model="user.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '邮箱不能为空'}]">
                <el-input v-model="user.email" type="email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址" :label-width="formLabelWidth">
                <el-cascader size="large" :options="options" v-model="selectedOptions" @change="handleChange"
                             style="width: 100%;">
                </el-cascader>
            </el-form-item>
            <el-form-item label="详细地址" :label-width="formLabelWidth" :rules="[{ required: true, message: '详细地址不能为空'}]">
                <el-input v-model="addressee.detailedAddress" type="email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="生日" prop="birthday" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '生日不能为空'}]">
                <el-date-picker
                    v-model="user.birthday"
                    type="date"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="年龄" prop="age" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '年龄不能为空'}]">
                <el-input v-model="user.age" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '性别不能为空'}]">
                <el-select v-model="user.sex" filterable placeholder="请选择对应的性别" :filter-method="init">
                    <el-option
                        v-for="item in sexList"
                        :key="item.key"
                        :label="item.name"
                        :value="item.key">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="权限" prop="role" :label-width="formLabelWidth">
                <el-select v-model="user.roleId" filterable placeholder="请选择对应的权限" :filter-method="init">
                    <el-option
                        v-for="item in roleList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="部门" prop="department" :label-width="formLabelWidth">
                <el-select v-model="user.departmentId" filterable placeholder="请选择对应的部门" :filter-method="init">
                    <el-option
                        v-for="item in departmentList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="薪资" prop="pay" :label-width="formLabelWidth"
                          :rules="[{ required: true, message: '薪资不能为空'}]">
                <el-input v-model="user.pay" type="number" autocomplete="off"></el-input>
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
import {User, Address} from "@/assets/script/model";
import message from "../../../assets/script/util/message";
import tips from "@/assets/script/util/tips";
import {regionData, CodeToText} from 'element-china-area-data'


export default {
    name: "UserCreateDialog",
    props: {
        dialogFormVisible: Boolean,
        disabled: Boolean,
    },
    data() {
        return {
            roleList: [],
            departmentList: [],
            idTimeDisabled: true,
            formLabelWidth: '120px',
            user: new User(),
            options: regionData,
            addressee: new Address(),
            selectedOptions: [],
            sexList: [
                {name: "男", key: "MAN"},
                {name: "女", key: "WOMAN"}
            ]
        }
    },
    watch: {
        dialogFormVisible() {
            this.init()
        }
    },
    methods: {
        ...mapActions("user", ["ACreate"]),
        ...mapActions("department", ["AListAll"]),
        ...mapActions("role", ["ARoleListAll"]),
        async init() {
            const departmentResult = await this.AListAll();
            if (departmentResult.status === 200) {
                this.departmentList = departmentResult.data
            } else {
                await tips.error(departmentResult.message);
            }
            const roleResult = await this.ARoleListAll();
            if (roleResult.status === 200) {
                this.roleList = roleResult.data
            } else {
                await tips.error(roleResult.message);
            }
        },
        async handleChange(arr) {
            this.addressee.province = CodeToText[arr[0]]
            this.addressee.city = CodeToText[arr[1]]
            this.addressee.area = CodeToText[arr[2]]
        },
        async save() {
            const user = this.user
            const address = this.addressee
            //新增
            if (user.name === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.phone === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.password === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.email === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.age === undefined || user.age < 0) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.sex === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.roleId === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.departmentId === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.birthday === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (user.pay === undefined || user.pay < 0) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (address.detailedAddress === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (address.province === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (address.city === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            if (address.area === undefined) {
                await tips.error("参数不能为空，请重新输入");
                return false;
            }
            user.addressee = {
                "province": address.province,
                "city": address.city,
                "area": address.area,
                "detailedAddress": address.detailedAddress
            }
            const result = await this.ACreate(user)
            if (result.status === 200) {
                message.messageSuccessAlert("添加成功")
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