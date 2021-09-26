# AchievementGrading_SpringBootAndVue
项目技术：springboot,vue,elmentUI,mybatis,druid,redis,spring cloud等
项目背景为在自己学习了springboot和vue后想通过实际项目经验练手锻炼自己。该项目由我一个人完成，代码量为1w行左右。技术选型为vue+springboot实现前后端分离。
● 前端使用vue框架结合elementui组件快速搭建。
● 后端采用springboot编写，完全遵循restful设计风格，接口文档详细，数据库交互采用mybatis+druid，并采用了redis做到缓存优化。
● 同时为了模拟多服务分布式环境，同时在nacos中注册了多个相同的服务接口。通过不同的权重配比模拟了最少活跃数调用法，随机调用法等负载均衡策略。并模拟了服务降级等服务。
● 因为是第一次接触前后端分离的项目，在项目完成期间遇到了很多困难，如springboot跨域问题，vue的npm镜像安装，mybatis二级缓存整合redis等，通过找导师及网上找博客解决诸多问题，解决后把解决过程做成了笔记。在此期间收获良多，加深了自己的代码优化意识。
