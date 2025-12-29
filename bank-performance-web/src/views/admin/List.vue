<template>
    <div style="padding-top: 1%;padding-left: 0.5%">
        <div class="form">
            <el-form label-width="80px">
                <el-form-item label="姓名">
                    <el-input v-model="user.name" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="user.phone" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="user.email" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input :value="(user.sex === 'MAN')?'男':'女'" :disabled="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="部门" v-show="user.role !== '管理员'">
                    <el-input v-model="user.departmentName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="等级" v-show="user.role !== '管理员'">
                    <el-input v-model="user.role" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="user.age" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="生日">
                    <el-input v-model="user.birthday" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="地址">

                    <el-input
                        :value="user.addressee.province+'-'+user.addressee.city+'-'+user.addressee.area"
                        :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="详细地址">
                    <el-input v-model="user.addressee.detailedAddress" :disabled="true"></el-input>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>

<script>
import {mapActions, mapState} from "vuex"

export default {
    computed: {
        ...mapState("user", ["user"]),
    },
    components: {},
    name: "List",
    methods: {
        ...mapActions("performance", ["APaging"]),
        //初始化时执行
        async init() {
            console.log(this.user)
        },
    },
    //绑定数据
    data() {
        return {}
    },
    mounted() {
        this.init();
    }
}
</script>

<style scoped>
.form {
    width: 50vw;
    text-align: center;
}
</style>