

### 面向对象编程，拒绝面条代码。


# 数据库设计
[数据库源文件 DB-Design.mwb ](https://github.com/wuda0112/foundation/blob/master/DB-Design.mwb)

使用 **MySQL Workbench** 打开

包含的数据库表有用户体系，权限控制体系，店铺，商品，订单，消息通知体系，异步任务等等，持续更新中......

# 系统设计文档
[系统文档源文件 System-Design.mm](https://github.com/wuda0112/foundation/blob/master/System-Design.mm)

使用**FreeMind**或者**XMind**打开

包含数据库设计文档，系统约束说明，专有名词说明等等，持续更新中......

# wiki
[quick start](https://github.com/wuda0112/foundation/wiki)

# 模块简介
- foundation-lang：定义了很多工具类，常用的与业务无关的基础类，比如，[树形结构](https://github.com/wuda0112/foundation/tree/master/foundation-lang/src/main/java/com/wuda/foundation/lang/tree/)，[Snowflake唯一ID生成器](https://github.com/wuda0112/foundation/blob/master/foundation-lang/src/main/java/com/wuda/foundation/lang/keygen/KeyGeneratorSnowflake.java)等等。没有数据库相关的维护。

- foundation-commons：用于维护与业务无关，通用的数据库表，比如用于保存行政区划省市区的表
- foundation-user：维护用户体系，最主要的就是维护数据库表的完整性
- foundation-security：维护权限控制体系
- 未来还有店铺体系，商品体系，订单体系等等，持续更新中......

# 如何搭建完整系统
比如想做一个CMS系统，则引入foundation-lang，foundation-commons，foundation-user，以及即将提交的foundation-item等模块即可

# 简介
建房子需要打地基，软件开发也一样，优先的基础设施才能支撑复杂的业务。这个项目不是开箱即用的成品软件，而是一个中台项目，主要关注开发过程中常用的基本组件定义，包括
- 数据库设计，比如电商系统相关数据库设计，包括店铺，商品，订单，仓库等等
- 常用的模型抽象定义和实现，比如权限系统数据库设计，代码实现；消息通知模块数据库设计，代码实现等等
- 常用的工具代码，比如在数据库设计时通常使用ID/PID表示层级结构，这样的结构是可以统一生成树形结构的，包括给前端生成方便展示的树形结构
- 尽量使用正确的英文单词，在数据库设计以及程序代码命名时，如果开发时间太紧，通常很多就是在百度翻译下，很多时候，可能使用错了英文单词，但是，一旦使用，基本上就很难再有机会改动了，比如数据库表名，列表，在业务上线后，没有特别原因，你敢再去改动吗？因此，在这里，数据库命名的每个英文单词都经过很多验证的，比如看google,amazon同类功能中使用哪些单词，比如在youtube上搜索相关单词，看国外（主要是美国）是如何使用这个单词的，一图抵过千言万语，好的名词也能更好的表达意思
- 定义基本的约束，比如在数据库设计中，所有数据ID都不能是0，因为0倍用于特殊用途；比如主键ID预留一个范围的值给系统数据使用，等等，虽然看上去很简单，但是积少成多，并且文档化，这些看上去小的东西也会发挥大作用

这些东西很多人都想做，但是由于业务时间太紧，今天没做，明天没做，最后就再也不好做起来了，如果在业务系统还没开始之前，就开始做好这些事情，就像修房子之前就打好地基一样，后续开发和维护就会顺畅很多。
