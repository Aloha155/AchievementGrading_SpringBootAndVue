<template>
  <div style="padding-top: 1%;padding-left: 0.5%">
    <div>
      <div class="demo-input-suffix">
        <el-row>
          <el-col :span="4">
            <el-date-picker
                v-model="queryTime"
                type="month"
                placeholder="选择月">
            </el-date-picker>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" icon="el-icon-search" @click="queryTable()"></el-button>
          </el-col>
        </el-row>
      </div>

      <div>
        <el-table
            ref="table"
            :data="tableData"
            stripe
            style="text-align: center"
            width="1">
          <el-table-column prop="username" label="员工姓名"></el-table-column>
          <el-table-column prop="rate" label="等级"></el-table-column>
          <el-table-column prop="roleName" label="权限"></el-table-column>
          <el-table-column prop="rateTime" label="评分时间"></el-table-column>
          <el-table-column prop="remake" label="备注"></el-table-column>
        </el-table>
      </div>
      <div>
        <el-pagination
            background
            layout="prev, pager, next, jumper"
            :current-page="pageable.page"
            :total="totalElements"
            :page-size="pageable.size"
            @current-change="changePage">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions} from "vuex"
import tips from "@/assets/script/util/tips";
import {Pageable} from "@/assets/script/model";
import message from "@/assets/script/util/message";

export default {
  components: {},
  name: "List",
  methods: {
    ...mapActions("performance", ["APaging"]),
    //初始化时执行
    async init() {

    },
    async queryTable() {
      if (this.queryTime === '') {
        await tips.warn("搜索条件不能为空");
        return false;
      }
      const date = new Date(this.queryTime)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      await this.showTable(year, month)
    },
    async changePage(page) {
      this.pageable.page = page;
      const date = new Date(this.queryTime)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      await this.showTable(year, month)
    },
    async showTable(year, month) {
      const result = await this.APaging({year, month});
      if (result.status === 200) {
        const page = result.data;
        this.tableData = page.content;
        this.totalElements = page.totalElements;
      } else {
        await tips.error(result.message);
      }
    },
    async remove(id, name) {
      try {
        await this.$confirm(`此操作将删除${name}, 是否继续？`, '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        const data = await this.ARemove({id});
        if (data.status === 200) {
          message.messageSuccessAlert('删除成功')
        } else {
          message.messageErrorAlert('删除失败')
        }
        this.$router.go(0)
      } catch (e) {
        message.messageInfoAlert('已取消删除')
      }
    }
  },
  //绑定数据
  data() {
    return {
      getKeys(row) {
        return row.id
      },
      queryTime: '',
      tableData: [],
      totalElements: 0,
      pageable: new Pageable(),
    }
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>