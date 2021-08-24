<template>
  <el-dialog title="绩效评分" :visible="dialogFormVisible" :show-close="false">
    <el-form>
      <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
        <el-input v-model="userName" :disabled="true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="评分等级" prop="rate" :label-width="formLabelWidth">
        <el-select v-model="rate" filterable placeholder="请打分">
          <el-option
              v-for="item in performanceList"
              :key="item.key"
              :label="item.name"
              :value="item.key">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remake" :label-width="formLabelWidth">
        <el-input v-model="remake" type="text" autocomplete="off"></el-input>
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
import {performanceConstant} from "@/assets/script/constant";


export default {
  name: "CreatePerformanceDialog",
  props: {
    dialogFormVisible: Boolean,
    disabled: Boolean,
    id: Number
  },
  data() {
    return {
      performanceList: performanceConstant(),
      userName: '',
      rate: '',
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
    ...mapActions("user", ["AUserGet"]),
    ...mapActions("performance", ["ACreate"]),
    async init() {
      const loading = this.$loading();
      const userResult = await this.AUserGet({"userId": this.id})
      if (userResult.status === 200) {
        this.userName = userResult.data.name
      } else {
        await tips.error(userResult.message);
      }
      loading.close()
    },
    async save() {
      if (this.rate === undefined || this.rate === null) {
        message.messageErrorAlert("评分不能为空")
        return false
      }
      if (this.remake === undefined || this.remake === null) {
        message.messageErrorAlert("备注不能为空")
        return false
      }
      const data = {'userId': this.id, 'rate': this.rate, 'remake': this.remake}
      const result = await this.ACreate(data)
      if (result.status === 200) {
        message.messageSuccessAlert("评分成功")
        this.rate = ''
        this.remake = ''
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