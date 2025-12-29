<template>
    <div style="padding-top: 1%;padding-left: 0.5%">
        <div>
            <div class="demo-input-suffix">
                <el-select v-model="region" :disabled="(user.role !== '管理员')" filterable placeholder="请选择搜索的部门"
                           :filter-method="init">
                    <el-option
                        v-for="item in departmentList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
                <el-button type="primary" icon="el-icon-search" @click="querySeries()"></el-button>
                <el-button type="primary" @click="init()">显示全部</el-button>
                <el-button v-show="user.role === '管理员'" type="primary" @click="createUser">新增员工</el-button>
            </div>
            <div>
                <el-table
                    ref="table"
                    :data="tableData"
                    stripe
                    style="text-align: center"
                    width="1"
                    :row-key="getKeys"
                    :expand-row-keys="expands"
                    @row-click="clickRowHandle">
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <el-divider content-position="left">
                                <h3>{{ props.row.name }}-员工名单</h3>
                            </el-divider>
                            <div style="width: auto">
                                <el-row>
                                    <el-col :span="4">
                                        <el-input style="width: 10vw" v-model="userPhone"
                                                  placeholder="请选择搜索的手机号"></el-input>
                                    </el-col>
                                    <el-col :span="4">
                                        <el-input style="width: 10vw" v-model="userName"
                                                  placeholder="请选择搜索的姓名"></el-input>
                                    </el-col>
                                    <el-col :span="4">
                                        <el-button type="primary" icon="el-icon-search" @click="queryUser(props.row)">
                                            搜索
                                        </el-button>
                                    </el-col>
                                    <el-col :span="4">
                                        <el-button type="primary" @click="initUser(props.row)">显示全部</el-button>
                                    </el-col>
                                </el-row>
                            </div>
                            <el-table :data="userList">
                                <el-table-column label="员工编号" prop="id">
                                </el-table-column>
                                <el-table-column label="员工姓名" prop="name">
                                </el-table-column>
                                <el-table-column label="手机号" prop="phone">
                                </el-table-column>
                                <el-table-column label="邮箱" prop="email">
                                </el-table-column>
                                <el-table-column label="权限" prop="role">
                                </el-table-column>
                                <el-table-column label="地址" prod="addressee" width="auto">
                                    <template slot-scope="scope">
                                        <span>{{ scope.row.addressee.province }}-</span>
                                        <span>{{ scope.row.addressee.city }}-</span>
                                        <span>{{ scope.row.addressee.area }}-</span>
                                        <span>{{ scope.row.addressee.detailedAddress }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="性别" prop="sex">
                                    <template slot-scope="scope">
                                        <span v-show="scope.row.sex === 'MAN'">男</span>
                                        <span v-show="scope.row.sex === 'WOMAN'">女</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" width="auto">
                                    <template slot-scope="scope">
                                        <div v-show="showGrade(user,scope.row)">
                                            <el-row>
                                                <el-button type="success" icon="el-icon-check"
                                                           @click="createPerformance(scope.row.id)">评分
                                                </el-button>
                                            </el-row>
                                        </div>

                                        <div v-show="user.role === '管理员'">
                                            <el-row>
                                                <el-button type="primary" icon="el-icon-edit"
                                                           @click="updateUser(scope.row.id)">修改
                                                </el-button>
                                            </el-row>
                                            <el-row>
                                                <el-button type="danger" icon="el-icon-delete"
                                                           @click="remove(scope.row.id,scope.row.name)">删除
                                                </el-button>
                                            </el-row>
                                            <el-row>
                                                <el-button type="info" icon="el-icon-office-building"
                                                           @click="updateDepartment(scope.row.id)">更换部门
                                                </el-button>
                                            </el-row>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <div>
                                <el-pagination
                                    background
                                    layout="prev, pager, next, jumper"
                                    :current-page="userPageable.page"
                                    :total="userTotalElements"
                                    :page-size="userPageable.size"
                                    @current-change="changePage">
                                </el-pagination>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="id" label="部门编号"></el-table-column>
                    <el-table-column prop="name" label="部门名称"></el-table-column>
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
        <UserCreateDialog
            :dialogFormVisible.sync="createDialogVisible"
            :disabled="createDisabled">
        </UserCreateDialog>
        <UserUpdateDialog
            :dialogFormVisible.sync="updateDialogVisible"
            :disabled="updateDisabled"
            :id="id">
        </UserUpdateDialog>
        <UpdateDepartmentDialog
            :dialogFormVisible.sync="updateDepartmentDialogVisible"
            :disabled="updateDepartmentDisabled"
            :id="id">
        </UpdateDepartmentDialog>
        <CreatePerformanceDialog
            :dialogFormVisible.sync="createPerformanceDialogVisible"
            :disabled="createPerformanceDisabled"
            :id="id">
        </CreatePerformanceDialog>
    </div>
</template>

<script>
import {mapActions, mapState} from "vuex"
import tips from "@/assets/script/util/tips";
import {Pageable} from "@/assets/script/model";
import message from "@/assets/script/util/message";

export default {
    computed: {
        ...mapState("user", ["user"]),
    },
    components: {
        UserCreateDialog: () => import("./components/UserCreateDialog"),
        UserUpdateDialog: () => import("./components/UserUpdateDialog"),
        UpdateDepartmentDialog: () => import("./components/UpdateDepartmentDialog"),
        CreatePerformanceDialog: () => import("./components/CreatePerformanceDialog"),
    },
    name: "List",
    methods: {
        ...mapActions("user", ["ARemove"]),
        ...mapActions("department", ["AListAll", "APaging", "APagingUserById"]),
        //初始化时执行
        async init() {
            const departmentResult = await this.AListAll();
            if (departmentResult.status === 200) {
                this.departmentList = departmentResult.data
            } else {
                await tips.error(departmentResult.message);
            }
            if (this.user.role !== '管理员') {
                const [first] = this.departmentList.filter(it => it.name === this.user.departmentName)
                this.region = first.id
            } else {
                this.region = ''
            }
            await this.showTable({'id': this.region})
        },
        async querySeries() {
            if (this.region === '') {
                await tips.warn("搜索条件不能为空");
                return false;
            }
            await this.showTable({'id': this.region})
        },
        async queryUser(row) {
            await this.showInfo(row)
        },
        async initUser(row) {
            this.userPhone = undefined
            this.userName = undefined
            await this.showInfo(row)
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
        async createUser() {
            this.createDialogVisible = true
            this.createDisabled = true
        },
        async updateUser(id) {
            this.id = id
            this.updateDialogVisible = true
            this.updateDisabled = false
        },
        async updateDepartment(id) {
            this.id = id
            this.updateDepartmentDialogVisible = true
            this.updateDepartmentDisabled = false
        },
        async createPerformance(id) {
            this.id = id
            this.createPerformanceDialogVisible = true
            this.createPerformanceDisabled = false
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
                this.expands = this.expands.filter(it => it !== row.id)
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
                })
                const data = await this.ARemove({"userId": id})
                if (data.status === 200) {
                    message.messageSuccessAlert('删除成功')
                } else {
                    message.messageErrorAlert('删除失败')
                }
            } catch (e) {
                message.messageInfoAlert('已取消删除')
            }
        },
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
            updateDialogVisible: false,
            updateDisabled: false,
            updateDepartmentDialogVisible: false,
            updateDepartmentDisabled: false,
            createPerformanceDialogVisible: false,
            createPerformanceDisabled: false,
            id: 0,
            showGrade: function showGrade(user, row) {
                if (user.role === '管理员') {
                    return row.role === '主管'
                }
              console.log("row",row.name)
              console.log("user",row.departmentName)
                return user.role === '主管' && row.departmentName === user.departmentName && user.id !== row.id;
            }
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