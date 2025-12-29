<template>
  <el-dialog title="转移部门" :visible="dialogFormVisible" :show-close="false">
    <el-form>
      <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
        <el-input v-model="userName" :disabled="true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="原来的部门编号" prop="sourceDepartmentId" :label-width="formLabelWidth">
        <el-input v-model="sourceDepartmentId" :disabled="true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="原来的部门名称" prop="sourceDepartmentName" :label-width="formLabelWidth">
        <el-input v-model="sourceDepartmentName" :disabled="true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="转移后的的部门" prop="departmentId" :label-width="formLabelWidth">
        <el-select v-model="departmentId" filterable placeholder="请选择对应的部门">
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
import {mapActions} from "vuex"
import tips from "@/assets/script/util/tips";
import message from "../../../assets/script/util/message";


export default {
  name: "UpdateDepartmentDialog",
  props: {
    dialogFormVisible: Boolean,
    disabled: Boolean,
    id: Number
  },
  data() {
    return {
      departmentList: [],
      userName: '',
      sourceDepartmentName: '',
      sourceDepartmentId: 0,
      departmentId: null,
      formLabelWidth: '120px'
    }
  },
  watch: {
    dialogFormVisible() {
      this.init()
    }
  },
  methods: {
    ...mapActions("user", ["AUpdate", "AUserGet"]),
    ...mapActions("department", ["AListAll", "AUpdateUserDepartment"]),
    async init() {
      const loading = this.$loading();
      const departmentResult = await this.AListAll();
      if (departmentResult.status === 200) {
        this.departmentList = departmentResult.data
      } else {
        await tips.error(departmentResult.message);
      }
      const userResult = await this.AUserGet({"userId": this.id})
      if (userResult.status === 200) {
        const user = userResult.data
        this.userName = user.name
        for (let department of this.departmentList) {
          if (department.name === user.departmentName) {
            this.sourceDepartmentId = department.id
            this.sourceDepartmentName = department.name
          }
        }
      } else {
        await tips.error(userResult.message);
      }
      loading.close()
    },
    async save() {
      if (this.departmentId === undefined || this.departmentId === null){
        message.messageErrorAlert("目标部门不能为空")
        return false
      }
      const data = {'sourceId': this.sourceDepartmentId, 'userId': this.id, 'targetId': this.departmentId}
      const result = await this.AUpdateUserDepartment(data)
      if (result.status === 200) {
        message.messageSuccessAlert("转移成功")
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