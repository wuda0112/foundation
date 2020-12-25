

### ---Java中台项目，Web开发也能面向对象编程，拒绝面条代码。---

# 数据库设计
- 目前已经有54个表
- [数据库源文件 DB-Design.mwb ](https://github.com/wuda0112/foundation/blob/master/DB-Design.mwb)

- 使用 **MySQL Workbench** 打开

持续更新中......

# 系统设计文档
- [系统文档源文件 System-Design.mm](https://github.com/wuda0112/foundation/blob/master/System-Design.mm)

- 使用**FreeMind**打开

包含数据库设计文档，系统约束说明，专有名词说明等等，持续更新中......

# wiki
[quick start](https://github.com/wuda0112/foundation/wiki)

# 模块简介
- foundation-lang： 定义了很多工具类，常用的与业务无关的基础类，比如，[树形结构](https://github.com/wuda0112/foundation/tree/master/foundation-lang/src/main/java/com/wuda/foundation/lang/tree/)，[Snowflake唯一ID生成器](https://github.com/wuda0112/foundation/blob/master/foundation-lang/src/main/java/com/wuda/foundation/lang/keygen/KeyGeneratorSnowflake.java)等等。没有数据库相关的维护。
- foundation-jooq: 使用[jooq](https://www.jooq.org/)作为ORM框架，因此，在这个模块中封装了很多逻辑，并且代码生成也在这个模块中
- foundation-core: 所有的数据库管理都在这个模块中，根据数据库的Scheme分类，对应相应的程序Package，包括
- - [commons](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/commons)： 用于维护与业务无关，通用的数据库表，比如维护email,phone,通用的实体属性等
- - [property](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/commons/property): 通用的属性，比如电商系统中，商品的属性
- - [DataType](https://github.com/wuda0112/foundation/tree/master/foundation-lang/src/main/java/com/wuda/foundation/lang/datatype): 自定义数据类型，和Property结合，可以定义Property的数据类型
- - [item](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/item): 维护Item模块，Item可以表示很多概念，比如最常见的商品
- - [notification](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/notification): 通知体系
- - [security](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/security)： 维护权限控制体系
- - [menu](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/security/menu): 角色的菜单权限，可以实现类似 Windows 的文件系统的权限分配效果
- - [store](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/store): 维护店铺模块
- - [user](https://github.com/wuda0112/foundation/tree/master/foundation-core/src/main/java/com/wuda/foundation/core/user)： 维护用户体系，最主要的就是维护数据库表的完整性
- - 持续更新中......

# 推荐的优势
- 数据库中台项目，不耦合业务，基于这个中台可以发展出各种业务
- 像使用开源组件一样，100%面向对象编程，绝对不是dao,mapper这样一路下来的面条代码
- 数据库设计很抽象，不为特点业务开发，比如像权限体系，很多都是user-role这样的模式，而我们这里使用subject,target,action等这样的抽象概念，参考[JAAS](https://docs.oracle.com/javase/7/docs/technotes/guides/security/jaas/JAASRefGuide.html)，几乎可以表示所有的权限体系。
- 丰富的文档，注释
