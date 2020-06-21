package com.wuda.foundation.lang;

/**
 * 由于我们是面向数据库的应用,在实际开发过程中,很多实体被用于数据库CRUD操作时传递参数,
 * 但是实体中一般有很多字段,而CRUD操作需要的字段是不一样的,比如对于更新操作,是不需要
 * (或者不能)修改记录的创建者字段的,对于删除操作,可能仅仅只需要记录的ID字段,其他字段都不
 * 需要设置值,如果一个实体被用于CRUD操作,明确的表明具体是哪种操作对于参数检查是很重要的,
 * 这样就可以解决在实例化对象时不知道应该为哪些参数设置值的问题.
 * <p>
 * 参考
 * <ul>
 * <li><a href = "https://docs.microsoft.com/en-us/ef/ef6/saving/change-tracking/entity-state">Working with entity states</a></li>
 * <li><a href = "https://www.entityframeworktutorial.net/crud-operation-in-connected-scenario-entity-framework.aspx">Saving Data in the Connected Scenario</a></li>
 * </ul>
 *
 * @author wuda
 * @see CRUDEntity.CRUDEntityBuilder
 * @since 1.0.0
 */
public enum CRUDState {

    /**
     * 实体不存在,准备insert到数据库中.
     */
    CREATE,
    /**
     * 从数据库中读取的信息被设置到实体中.
     */
    READ,
    /**
     * 实体已经存在,准备update.
     */
    UPDATE,
    /**
     * 实体已经存在,准备delete.
     */
    DELETE;
}
