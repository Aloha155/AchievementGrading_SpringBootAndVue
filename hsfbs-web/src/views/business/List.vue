<template>
    <div style="padding-top: 1%;padding-left: 0.5%">
        <div>
            <div class="demo-input-suffix">
                <el-row>
                    <div v-show="showTableDisabled">
                        <el-row>
                            <el-col :span="4">
                                <el-select v-model="departmentId"
                                           style="width: 100%"
                                           :disabled="(user.role !== '管理员')"
                                           placeholder="请选择查询部门">
                                    <el-option
                                        v-for="item in departmentList"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4">
                                <el-input v-model="businessName" placeholder="请输入业务名" autocomplete="off"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-input v-model="businessCode" placeholder="请输入编码" autocomplete="off"></el-input>
                            </el-col>
                        </el-row>
                        <el-col :span="4">
                            <el-button type="primary" icon="el-icon-search" @click="queryTable()"></el-button>
                            <el-button type="primary" icon="el-icon-search" @click="queryAll()">显示全部</el-button>
                        </el-col>
                        <el-col :span="4" v-show="(user.role === '管理员')">
                            <el-button type="success" @click="createBusiness">新增可以办业务</el-button>
                        </el-col>
                        <el-col :span="4">
                            <el-button type="primary" @click="showHisBusiness">查看业务历史记录</el-button>
                        </el-col>
                    </div>
                    <div v-show="showHisTableDisabled">
                        <el-row>
                            <el-col :span="4">
                                <el-select v-model="hisQueryParam.departmentId"
                                           style="width: 100%"
                                           :disabled="(user.role !== '管理员')"
                                           placeholder="请选择查询部门">
                                    <el-option
                                        v-for="item in hisQueryParam.departmentList"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-row>
                        <el-row v-show="user.role !== '员工'">
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.adminName" placeholder="请输入业务员姓名"
                                          autocomplete="off"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.adminPhone" placeholder="请输入业务员手机号码"
                                          autocomplete="off"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.businessName" placeholder="请输入业务名"
                                          autocomplete="off"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.businessCode" placeholder="请输入业务编码"
                                          autocomplete="off"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.username" placeholder="请输入办理业务人员的姓名"
                                          autocomplete="off"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-input v-model="hisQueryParam.phone" placeholder="请输入业务员手机号码"
                                          autocomplete="off"></el-input>
                            </el-col>
                        </el-row>
                        <el-col :span="4">
                            <el-button type="primary" icon="el-icon-search" @click="queryHisTable()"></el-button>
                            <el-button type="primary" icon="el-icon-search" @click="queryHisAll()">显示全部</el-button>
                        </el-col>
                        <el-col :span="4">
                            <el-button type="primary" @click="showBusiness">查看可办业务</el-button>
                        </el-col>
                    </div>
                </el-row>
            </div>
            <div v-show="showTableDisabled">
                <div>
                    <el-table
                        ref="table"
                        :data="adminTableData"
                        stripe
                        style="text-align: center"
                        width="1">
                        <el-table-column prop="name" label="业务名"></el-table-column>
                        <el-table-column prop="code" label="业务编码"></el-table-column>
                        <el-table-column prop="departmentName" label="所属部门"></el-table-column>
                        <el-table-column prop="state" label="业务状态">
                            <template slot-scope="scope">
                                <span v-show="scope.row.state === 'PASS'">正在使用</span>
                                <span v-show="scope.row.state === 'DISABLED'">已禁用</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="auto">
                            <template slot-scope="scope">
                                <div v-show="user.role === '管理员'">
                                    <el-row v-show="scope.row.state === 'PASS'">
                                        <el-button type="danger" icon="el-icon-check"
                                                   @click="businessDisabled(scope.row.id,scope.row.name)">禁用
                                        </el-button>
                                    </el-row>
                                    <div v-show="scope.row.state === 'DISABLED'">
                                        <el-row>
                                            <el-button type="success" icon="el-icon-check"
                                                       @click="businessPass(scope.row.id,scope.row.name)">启用
                                            </el-button>
                                        </el-row>
                                        <el-row>
                                            <el-button type="primary" icon="el-icon-check"
                                                       @click="update(scope.row.id)">修改
                                            </el-button>
                                        </el-row>
                                    </div>
                                </div>
                                <div v-show="user.role === '员工'">
                                    <el-row>
                                        <el-button type="primary" v-show="scope.row.state === 'PASS'"
                                                   icon="el-icon-check"
                                                   @click="transactionBusiness(scope.row.id)">办理
                                        </el-button>
                                        <el-button type="danger" :disabled="true"
                                                   v-show="scope.row.state === 'DISABLED'"
                                                   icon="el-icon-check">该业务不能办理
                                        </el-button>
                                    </el-row>
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
            <div v-show="showHisTableDisabled">
                <div>
                    <el-table
                        ref="hisBusinessTable"
                        :data="hisBusinessData"
                        stripe
                        style="text-align: center"
                        width="1">
                        <el-table-column type="expand">
                            <template slot-scope="props">
                                <el-form label-position="left" inline class="demo-table-expand">
                                    <el-form-item label="业务名">
                                        <span>{{ props.row.businessName }}</span>
                                    </el-form-item>
                                    <el-form-item label="业务编码">
                                        <span>{{ props.row.businessCode }}</span>
                                    </el-form-item>
                                    <el-form-item label="所属部门编号">
                                        <span>{{ props.row.departmentId }}</span>
                                    </el-form-item>
                                    <el-form-item label="所属部门名称">
                                        <span>{{ props.row.departmentName }}</span>
                                    </el-form-item>
                                    <el-form-item label="办理业务员工编码">
                                        <span>{{ props.row.userId }}</span>
                                    </el-form-item>
                                    <el-form-item label="办理业务员工姓名">
                                        <span>{{ props.row.adminName }}</span>
                                    </el-form-item>
                                    <el-form-item label="业务办理人姓名">
                                        <span>{{ props.row.username }}</span>
                                    </el-form-item>
                                    <el-form-item label="业务办理人电话">
                                        <span>{{ props.row.phone }}</span>
                                    </el-form-item>
                                    <el-form-item label="当前状态" class="state">
                                        <el-tag v-show="props.row.userBusinessState === 'CREATE'">业务处理中</el-tag>
                                        <el-tag color="success" v-show="props.row.userBusinessState === 'SUCCESS'">办理成功</el-tag>
                                        <el-tag color="info" v-show="props.row.userBusinessState === 'UPDATE'">以提交修改</el-tag>
                                        <el-tag v-show="props.row.userBusinessState === 'UPDATE_SUCCESS'">修改成功</el-tag>
                                        <el-tag color="warning" v-show="props.row.userBusinessState === 'UPDATE_ERROR'">驳回修改
                                        </el-tag>
                                        <el-tag color="danger" v-show="props.row.userBusinessState === 'ERROR'">失败</el-tag>
                                        <el-tag type="danger" v-show="showWar(props.row)">处理超时</el-tag>
                                    </el-form-item>
                                    <el-form-item label="用户备注">
                                        <span>{{ props.row.remake }}</span>
                                    </el-form-item>
                                    <el-form-item label="提交修改备注">
                                        <span>{{ props.row.auditCause }}</span>
                                    </el-form-item>
                                    <el-form-item label="驳回备注">
                                        <span>{{ props.row.auditRemake }}</span>
                                    </el-form-item>
                                </el-form>
                            </template>
                        </el-table-column>
                        <el-table-column prop="id" label="序号"></el-table-column>
                        <el-table-column prop="businessName" label="业务名"></el-table-column>
                        <el-table-column prop="adminName" label="办理业务员工姓名"></el-table-column>
                        <el-table-column prop="createDate" label="创建时间"></el-table-column>
                        <el-table-column prop="username" label="办理人姓名"></el-table-column>
                        <el-table-column prop="phone" label="办理人电话"></el-table-column>
                        <el-table-column prop="userBusinessState" label="业务状态">
                            <template slot-scope="scope">
                                <el-tag v-show="scope.row.userBusinessState === 'CREATE'">业务处理中</el-tag>
                                <el-tag color="success" v-show="scope.row.userBusinessState === 'SUCCESS'">办理成功</el-tag>
                                <el-tag color="info" v-show="scope.row.userBusinessState === 'UPDATE'">以提交修改</el-tag>
                                <el-tag v-show="scope.row.userBusinessState === 'UPDATE_SUCCESS'">修改成功</el-tag>
                                <el-tag color="warning" v-show="scope.row.userBusinessState === 'UPDATE_ERROR'">驳回修改
                                </el-tag>
                                <el-tag color="danger" v-show="scope.row.userBusinessState === 'ERROR'">失败</el-tag>
                                <el-tag type="danger" v-show="showWar(scope.row)">处理超时</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="auto">
                            <template slot-scope="scope">
                                <div v-show="user.role === '员工'">
                                    <div
                                        v-show="scope.row.userBusinessState === 'CREATE' || scope.row.userBusinessState === 'UPDATE_SUCCESS' ">
                                        <el-row>
                                            <el-button type="success"
                                                       icon="el-icon-check"
                                                       @click="businessSuccess(scope.row.id,scope.row.name)">结束
                                            </el-button>
                                        </el-row>
                                        <el-row>
                                            <el-button type="danger"
                                                       icon="el-icon-check"
                                                       @click="businessError(scope.row.id,scope.row.name)">失败
                                            </el-button>
                                        </el-row>
                                        <el-row>
                                            <el-button type="primary"
                                                       icon="el-icon-check"
                                                       @click="businessUpdate(scope.row.id)">修改
                                            </el-button>
                                        </el-row>
                                    </div>
                                </div>
                                <div v-show="user.role === '主管'">
                                    <el-row>
                                        <el-button type="danger" v-show="scope.row.userBusinessState === 'UPDATE'"
                                                   icon="el-icon-check"
                                                   @click="auditError(scope.row.id,scope.row.name)">驳回
                                        </el-button>
                                    </el-row>
                                    <el-row>
                                        <el-button type="success" v-show="scope.row.userBusinessState === 'UPDATE'"
                                                   icon="el-icon-check"
                                                   @click="auditSuccess(scope.row.id,scope.row.name)">修改
                                        </el-button>
                                    </el-row>
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
            <CreateBusinessDialog
                :dialogFormVisible.sync="createDialogVisible"
                :disabled="createDisabled">
            </CreateBusinessDialog>
            <UpdateBusinessDialog
                :dialogFormVisible.sync="updateDialogVisible"
                :disabled="updateDisabled"
                :id="id">
            </UpdateBusinessDialog>
            <TransactionBusinessDialog
                :dialogFormVisible.sync="transactionBusinessDialogVisible"
                :disabled="transactionBusinessDisabled"
                :id="id">
            </TransactionBusinessDialog>
            <UpdateUserBusinessDialog
                :dialogFormVisible.sync="updateUserBusinessDialogVisible"
                :disabled="UpdateUserBusinessDisabled"
                :id="id">
            </UpdateUserBusinessDialog>
        </div>
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
        CreateBusinessDialog: () => import("./components/CreateBusinessDialog"),
        UpdateBusinessDialog: () => import("./components/UpdateBusinessDialog"),
        TransactionBusinessDialog: () => import("./components/TransactionBusinessDialog"),
        UpdateUserBusinessDialog: () => import("./components/UpdateUserBusinessDialog"),
    },
    name: "List",
    methods: {
        ...mapActions("department", ["AListAll"]),
        ...mapActions("user", ["AUserGet"]),
        ...mapActions("userBusiness", ["AHisPaging", "AUpdateUserBusinessState", "ACreateAudit", "AAudit", "AUpdate"]),
        ...mapActions("business", ["APaging", "AUpdate", "AUpdateState", "ARemove"]),
        //初始化时执行
        async init() {
            const departmentResult = await this.AListAll();
            if (departmentResult.status === 200) {
                this.departmentList = departmentResult.data
            } else {
                await tips.error(departmentResult.message);
            }
            if (this.user.role !== "管理员") {
                for (let department of this.departmentList) {
                    if (department.name === this.user.departmentName) {
                        this.departmentId = department.id
                    }
                }
            }
            await this.showTable(undefined, undefined, this.departmentId)
        },
        async queryTable() {
            await this.showTable(this.businessName, this.businessCode, this.departmentId)
        },
        async changePage(page) {
            this.pageable.page = page;
            await this.showTable(this.businessName, this.businessCode, this.departmentId)
        },
        async queryAll() {
            if (this.user.role === "管理员") {
                this.departmentId = ''
            }
            this.businessCode = ''
            this.businessName = ''
            await this.showTable(undefined, undefined, this.departmentId)
        },
        async showTable(name, code, departmentId) {
            const data = {name, code, departmentId}
            const result = await this.APaging(data)
            if (result.status === 200) {
                const page = result.data;
                this.adminTableData = page.content;
                this.totalElements = page.totalElements;
            } else {
                await tips.error(result.message);
            }
        },
        async businessDisabled(id, name) {
            try {
                await this.$confirm(`此操作将禁用${name}, 是否继续？`, '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                const result = await this.AUpdateState({id, "businessState": "DISABLED"})
                if (result.status === 200) {
                    message.messageSuccessAlert('禁用成功')
                } else {
                    message.messageErrorAlert('禁用失败')
                }
                this.$router.go(0)
            } catch (e) {
                message.messageInfoAlert('已取消禁用')
            }
        },
        async businessPass(id, name) {
            try {
                await this.$confirm(`此操作将启动${name}, 是否继续？`, '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                const result = await this.AUpdateState({id, "businessState": "PASS"})
                if (result.status === 200) {
                    message.messageSuccessAlert('启动成功')
                } else {
                    message.messageErrorAlert('启动失败')
                }
                this.$router.go(0)
            } catch (e) {
                message.messageInfoAlert('已取消启动')
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
        },
        async update(id) {
            this.id = id
            this.updateDialogVisible = true
            this.updateDisabled = false
        },

        async businessSuccess(id) {
            try {
                await this.$confirm(`请确认当前业务是否处理成功？`, '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                const result = await this.AUpdateUserBusinessState({id, "userBusinessState": "SUCCESS"})
                if (result.status === 200) {
                    message.messageSuccessAlert('业务已完成')
                } else {
                    message.messageErrorAlert('业务完成失败')
                }
            } catch (e) {
                message.messageInfoAlert('业务完成失败')
            }
            await this.showHisBusiness()
        },
        async businessError(id) {
            try {
                await this.$confirm(`请确认当前业务是否处理失败？`, '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                const result = await this.AUpdateUserBusinessState({id, "userBusinessState": "ERROR"})
                if (result.status === 200) {
                    message.messageSuccessAlert('业务已取消')
                } else {
                    message.messageErrorAlert('业务取消失败')
                }
            } catch (e) {
                message.messageInfoAlert('业务取消失败')
            }
            await this.showHisBusiness()
        },
        async businessUpdate(id) {
            this.$prompt('请输入详细描述要修改当前业务的地方', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(async ({value}) => {
                if (value === undefined || value === null) {
                    message.messageInfoAlert('提交修改意见不能为空')
                    return false;
                }
                const result = await this.ACreateAudit({id, "cause": value})
                if (result.status === 200) {
                    message.messageSuccessAlert('提交成功')
                }
            }).catch(() => {
                message.messageInfoAlert('取消修改申请')
            });
            await this.showHisBusiness()
        },
        async auditError(id) {
            this.$prompt('请输入驳回意见', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(async ({value}) => {
                if (value === undefined || value === null) {
                    message.messageInfoAlert('提交修改意见不能为空')
                    return false;
                }
                const result = await this.AAudit({id, "userBusinessState": "UPDATE_ERROR", "auditRemake": value})
                if (result.status === 200) {
                    message.messageSuccessAlert('提交成功')
                }
            }).catch(() => {
                message.messageInfoAlert('取消修改申请')
            });
            await this.showHisBusiness()
        },
        async queryHisTable() {
            const result = await this.AHisPaging(this.hisQueryParam)
            if (result.status === 200) {
                const page = result.data;
                this.hisBusinessData = page.content;
                this.totalElements = page.totalElements;
            } else {
                await tips.error(result.message);
            }
        },
        async queryHisAll() {
            this.hisQueryParam.adminName = ''
            this.hisQueryParam.adminPhone = ''
            this.hisQueryParam.businessCode = ''
            this.hisQueryParam.businessName = ''
            this.hisQueryParam.username = ''
            this.hisQueryParam.phone = ''
            if (this.user.role === '管理员') {
                this.hisQueryParam.departmentId = ''
            }
            const result = await this.AHisPaging(this.hisQueryParam)
            if (result.status === 200) {
                const page = result.data;
                this.hisBusinessData = page.content;
                this.totalElements = page.totalElements;
            } else {
                await tips.error(result.message);
            }
        },
        async showHisBusiness() {
            this.showHisTableDisabled = true
            this.showTableDisabled = false
            this.hisQueryParam.departmentList = this.departmentList
            this.hisQueryParam.departmentId = this.departmentId
            const result = await this.AHisPaging(this.hisQueryParam)
            if (result.status === 200) {
                const page = result.data;
                this.hisBusinessData = page.content;
                this.totalElements = page.totalElements;
            } else {
                await tips.error(result.message);
            }
        },
        async auditSuccess(id) {
            this.id = id
            this.UpdateUserBusinessDisabled = true
            this.updateUserBusinessDialogVisible = true
        },
        async showBusiness() {
            this.showHisTableDisabled = false
            this.showTableDisabled = true
            await this.init();
        },
        async createBusiness() {
            this.createDialogVisible = true
            this.createDisabled = true
        },
        async transactionBusiness(id) {
            this.transactionBusinessDialogVisible = true
            this.transactionBusinessDisabled = false
            this.id = id
        },
    },
//绑定数据
    data() {
        return {
            adminTableData: [],
            hisBusinessData: [],
            totalElements: 0,
            pageable: new Pageable(),
            createDialogVisible: false,
            createDisabled: false,
            updateDialogVisible: false,
            updateDisabled: false,
            transactionBusinessDialogVisible: false,
            transactionBusinessDisabled: false,
            updateUserBusinessDialogVisible: false,
            UpdateUserBusinessDisabled: false,
            departmentId: '',
            roleDis: false,
            departmentList: [],
            businessName: '',
            businessCode: '',
            id: 0,
            showTableDisabled: true,
            showHisTableDisabled: false,
            hisQueryParam: {
                departmentId: '',
                departmentList: [],
                username: '',
                phone: '',
                adminName: '',
                adminPhone: '',
                businessName: '',
                businessCode: '',
            },
            phone: '',
            showWar: function showWarning(row) {
                const time = new Date().getTime() - new Date(row.createDate).getTime()
                return row.userBusinessState === 'CREATE' && time > 1000;
            }
        }
    }
    ,
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
.state el-tag{
    margin: 1px;
}
</style>