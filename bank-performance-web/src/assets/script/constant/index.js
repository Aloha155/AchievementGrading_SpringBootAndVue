import {BusinessEnum, MenuEnum, PerformanceEnum, Sex} from "../model"

export function menuListConstant() {
    return [
        new MenuEnum("系统", undefined, "el-icon-s-data", [
            new MenuEnum("部门", "/department", "el-icon-s-data"),
            new MenuEnum("绩效", "/performance", "el-icon-s-data"),
            new MenuEnum("用户", "/user", "el-icon-s-data"),
            new MenuEnum("业务", "/business", "el-icon-s-data")
        ]),
        new MenuEnum("个人信息", undefined, "el-icon-s-data", [
            new MenuEnum("个人信息", "/admin", "el-icon-s-data")
        ]),
    ]
}

export function sexConstant() {
    return [
        new Sex("男", "MAN"),
        new Sex("女", "WOMAN"),
    ]
}

export function performanceConstant() {
    return [
        new PerformanceEnum("A", "A"),
        new PerformanceEnum("B", "B"),
        new PerformanceEnum("C", "C"),
        new PerformanceEnum("D", "D"),
    ]
}

export function businessStateConstant() {
    return [
        new BusinessEnum("PASS", "使用"),
        new BusinessEnum("DISABLED", "禁用"),
    ]
}

export function businessCompletenessConstant() {
    return [
        new BusinessEnum("CREATE", "创建成功"),
        new BusinessEnum("SUCCESS", "办理成功"),
        new BusinessEnum("ERROR", "办理失败"),
        new BusinessEnum("UPDATE", "申请修改"),
        new BusinessEnum("UPDATE_SUCCESS", "修改成功"),
        new BusinessEnum("UPDATE_ERROR", "驳回修改")
    ]
}