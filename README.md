# 银行支行绩效考核管理系统

> 基于 Spring Boot + Vue.js 的前后端分离企业级管理系统，实现银行支行的用户、部门、业务、绩效考核等核心功能管理。

**技术亮点：** 前后端分离 · JWT认证 · Redis缓存 · RESTful API · 响应式访问

## 🚀 快速开始

### 后端启动

```bash
cd bank-performance-api
mvn clean install
mvn spring-boot:run
```

后端服务：`http://localhost:8080`

### 前端启动

```bash
cd bank-performance-web
npm install
npm run serve
```

前端应用：`http://localhost:8081`

### 环境要求

**后端：** JDK 1.8+ · Maven 3.6+ · MySQL 8.0+ · Redis 5.0+  
**前端：** Node.js 12+ · npm 6+

## 📦 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.4.5 | 核心框架 |
| Spring Data JPA | 2.4.5 | ORM持久化 |
| Spring Data Redis | 2.4.5 | 响应式缓存 |
| MySQL | 8.x | 关系型数据库 |
| Druid | 1.1.10 | 连接池 |
| JWT | 0.9.0 | Token认证 |

### 前端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue.js | 2.6.11 | 渐进式框架 |
| Vue Router | 3.4.9 | 路由管理 |
| Vuex | 3.5.1 | 状态管理 |
| Element UI | 2.14.1 | UI组件库 |
| Axios | 0.21.0 | HTTP客户端 |

## 🎯 功能模块

### 用户管理
用户注册/登录 · 信息维护 · 角色分配 · 部门关联 · 密码管理

### 部门管理
部门维护 · 层级结构 · 业务关联 · 人员统计

### 业务管理
业务录入 · 状态跟踪 · 分配转移 · 统计报表

### 绩效考核
指标设定 · 数据录入 · 评分计算 · 结果查询 · 趋势分析

### 权限管理
RBAC权限控制 · 角色配置 · 菜单动态加载 · 接口级验证

### 系统管理
配置管理 · 操作日志 · 数据字典 · 地址信息

## 🏗️ 系统架构

```
┌─────────────────────────────────────────┐
│           前端层 (Vue.js)                │
│   Vue Router + Vuex + Element UI        │
└──────────────────┬──────────────────────┘
                   │ RESTful API
┌──────────────────┴──────────────────────┐
│        后端层 (Spring Boot)             │
│   Controller + Service + Validator      │
├─────────────────────────────────────────┤
│        数据层 (JPA + Redis)             │
│   DAO + Entity + Cache                  │
└──────────────────┬──────────────────────┘
                   │
        ┌──────────┴─────────┐
        │                    │
   ┌────┴─────┐      ┌──────┴──────┐
   │  MySQL   │      │    Redis    │
   └──────────┘      └─────────────┘
```

### 包结构

```
com.bank.performance
├── controller/         # 控制器层
├── service/           # 服务层
│   ├── impl/         # 服务实现
│   └── validation/   # 数据验证
├── dao/              # 数据访问层
├── entity/           # 实体类
├── constant/         # 常量/枚举
└── core/             # 核心组件
    ├── base/        # 基础类
    ├── config/      # 配置类
    ├── dto/         # 数据传输对象
    ├── vo/          # 视图对象
    ├── exception/   # 异常处理
    ├── interceptor/ # 拦截器
    └── util/        # 工具类
```

### 核心实体

**主实体：** User · Department · Business · Performance · Role · Address

**关联实体：** UserDepartment · UserRole · UserBusiness · DepartmentBusiness

## ✨ 核心特性

### 后端特性

- **RESTful API** - 统一请求响应格式，规范的状态码使用
- **JWT认证** - 无状态Token认证，拦截器验证权限
- **Redis缓存** - 响应式缓存优化，提升查询性能
- **异常处理** - 全局异常处理器，自定义异常体系
- **数据验证** - Hibernate Validator参数校验，业务逻辑分离

### 前端特性

- **组件化开发** - 全局组件复用，业务组件解耦
- **状态管理** - Vuex模块化管理，集中式状态控制
- **路由守卫** - 权限验证，动态路由配置
- **响应式设计** - Element UI快速开发，多端适配

## 📊 项目规模

- **代码量：** 约 10,000+ 行
- **开发模式：** 前后端分离
- **开发周期：** 个人独立开发
- **架构模式：** MVC + 分层架构
