<template>
  <el-dialog title="新增部门" :visible="dialogFormVisible" :show-close="false">
    <el-form :model="department">
      <el-form-item label="部门名称" :label-width="formLabelWidth">
        <el-input v-model="department.name" autocomplete="off"></el-input>
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
import {Department} from "@/assets/script/model";
import message from "../../../assets/script/util/message";

export default {
  name: "ArticleDialog",
  props: {
    dialogFormVisible: Boolean,
    disabled: Boolean,
  },
  data() {
    return {
      idTimeDisabled: true,
      formLabelWidth: '120px',
      department: new Department(),
    }
  },
  watch: {
    dialogFormVisible() {
      this.init()
    }
  },
  methods: {
    ...mapActions("department", ["ACreate"]),
    async init() {


    },
    async save() {
      //新增
      const param = {
        "name": this.department.name,
      }
      const result = await this.ACreate(param)
      if (result.status === 200) {
        message.messageSuccessAlert("添加成功")
        await this.close()
      } else {
        message.messageErrorAlert(result.message)
        this.department = new Department()
      }
    },
    async close() {
      await this.$emit("update:dialogFormVisible", false);
    }
  }
}
</script>

<style scoped type="text/css">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>