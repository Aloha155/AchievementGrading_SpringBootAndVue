export class Result {
    /**
     * 统一返回
     * @param {Number} status
     * @param {Object} data
     * @param {String}message
     */
    constructor(status, data, message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

export class LoginForm {
    constructor(phone, password) {
        this.phone = phone
        this.password = password
    }
}

export class MenuEnum {
    /**
     * 菜单枚举
     * @param {String}label
     * @param {String}path
     * @param {String}icon
     * @param {MenuEnum[]|undefined}child
     */
    constructor(label, path, icon, child = undefined) {
        this.label = label;
        this.path = path;
        this.icon = icon;
        this.child = child;
    }
}

export class Pageable {
    /**
     * 分页
     * @param {*}page
     * @param {*}size
     * @param {String}sort
     */
    constructor(page = 1, size = 20, sort = "") {
        this.page = parseInt(page || 1);
        this.size = parseInt(size);
        this.sort = sort;
    }
}

export class Article {
    constructor(id, images, title, content, series, createDate) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.content = content;
        this.series = series;
        this.createDate = createDate
    }
}

export class Department {
    constructor(id, name, createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }
}

export class User {
    constructor(id, name, password, age, phone, registerTime, email, addressee, birthday, sex, roleId, departmentId, pay) {
        this.id = id
        this.name = name
        this.password = password
        this.age = age
        this.phone = phone
        this.registerTime = registerTime
        this.email = email
        this.addressee = addressee
        this.birthday = birthday
        this.sex = sex
        this.roleId = roleId
        this.departmentId = departmentId
        this.pay = pay
    }
}

export class Address {
    constructor(province, city, area, detailedAddress) {
        this.province = province
        this.city = city
        this.area = area
        this.detailedAddress = detailedAddress
    }
}

export class Sex {
    constructor(name, key) {
        this.name = name
        this.key = key
    }
}

export class PerformanceEnum {
    constructor(key, name) {
        this.key = key
        this.name = name
    }
}

export class BusinessEnum {
    constructor(key, name) {
        this.key = key
        this.name = name
    }
}

export class UserBusiness {
    constructor(userId, name, departmentId, departmentName, businessId, businessName, businessCode, username, phone, remake, auditCause, auditRemake, userBusinessState) {
        this.userId = userId
        this.name = name
        this.departmentId = departmentId
        this.departmentName = departmentName
        this.businessId = businessId
        this.businessName = businessName
        this.businessCode = businessCode
        this.username = username
        this.phone = phone
        this.remake = remake
        this.auditCause = auditCause
        this.auditRemake = auditRemake
        this.userBusinessState = userBusinessState
    }

}