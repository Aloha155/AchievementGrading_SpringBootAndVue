<template>
  <div style="padding-top: 1%;padding-left: 0.5%">
    <div>
      <div class="demo-input-suffix">
        <el-select v-model="region" filterable placeholder="请选择搜索的部门" :filter-method="init">
          <el-option
              v-for="item in departmentList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click="querySeries()"></el-button>
        <el-button type="primary" @click="init()">显示全部</el-button>
        <el-button v-show="user.role === '管理员'" type="primary" @click="showCreate()">新增部门</el-button>
      </div>
      <div>
        <el-table
            ref="table"
            :data="tableData"
            stripe
            style="text-align: center"
            width="1">
          <el-table-column prop="id" label="部门编号"></el-table-column>
          <el-table-column prop="name" label="部门名称"></el-table-column>
          <el-table-column prop="createDate" label="创建时间"></el-table-column>
          <el-table-column label="操作" width="500vw">
            <template slot-scope="scope">
                <div v-show="user.role === '管理员'">
                    <el-button type="primary" icon="el-icon-edit" @click="update(scope.row.id)">修改信息</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click="remove(scope.row.id,scope.row.name)">删除部门
                    </el-button>
                </div>
            </template>
          </el-table-column>
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
    <DepartmentUpdateDialog
        :dialogFormVisible.sync="dialogUpdateVisible"
        :disabled="updateDisabled"
        :id="id">
    </DepartmentUpdateDialog>
    <DepartmentCreateDialog
        :dialogFormVisible.sync="createDialogVisible"
        :disabled="createDisabled">
    </DepartmentCreateDialog>
  </div>
</template>

<script>
import {mapActions,mapState} from "vuex"
import tips from "@/assets/script/util/tips";
import {Pageable} from "@/assets/script/model";
import message from "@/assets/script/util/message";

export default {
    computed: {
        ...mapState("user", ["user"]),
    },
  components: {
    DepartmentCreateDialog: () => import("./components/DepartmentCreateDialog"),
    DepartmentUpdateDialog: () => import("./components/DepartmentUpdateDialog"),
  },
  name: "List",
  methods: {
    ...mapActions("department", ["AListAll", "APaging", "ARemove", "APagingUserById"]),
    //初始化时执行
    async init() {
      await this.showTable();
      this.region = ''
      const departmentResult = await this.AListAll();
      if (departmentResult.status === 200) {
        this.departmentList = departmentResult.data
      } else {
        await tips.error(departmentResult.message);
      }
    },
    async querySeries() {
      if (this.region === '') {
        await tips.warn("搜索条件不能为空");
        return false;
      }
      await this.showTable({'id': this.region})
    },
    async changePage(page) {
      this.pageable.page = page;
      await this.showTable(Object.assign({}, this.pageable, {"id": this.region}))
    },
    async showTable(param) {
      const result = await this.APaging(param);
      if (result.status === 200) {
        const page = result.data;
        this.tableData = page.content;
        this.totalElements = page.totalElements;
      } else {
        await tips.error(result.message);
      }
    },
    async update(id) {
      this.dialogUpdateVisible = true
      this.id = id
      this.updateDisabled = false;
    },
    async showCreate() {
      this.createDialogVisible = true
      this.createDisabled = true;
    },
    async showInfo(row) {
      const param = {
        "id": row.id,
        "name": this.userName,
        "phone": this.userPhone
      }
      const result = await this.APagingUserById(param);
      if (result.status === 200) {
        const page = result.data;
        this.userList = page.content;
        this.userTotalElements = page.totalElements;
      } else {
        await tips.error(result.message);
      }
    },
    async clickRowHandle(row) {
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id)
      } else {
        //判断是否已经存在展开的行
        if (this.expands.length !== 0) {
          //如果存在展开行,清空expands数组,使它关闭
          this.expands.splice(0, this.expands.length)
        }
        //打开点击的行
        await this.showInfo(row)
        this.expands.push(row.id)
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
      expands: [],
      departmentList: [],
      tableData: [],
      userList: [],
      input: '',
      formLabelWidth: '80px',
      region: '',
      userPhone: '',
      userName: '',
      totalElements: 0,
      userTotalElements: 0,
      pageable: new Pageable(),
      userPageable: new Pageable(),
      createDialogVisible: false,
      createDisabled: false,
      dialogUpdateVisible: false,
      updateDisabled: false,
      id: 0
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