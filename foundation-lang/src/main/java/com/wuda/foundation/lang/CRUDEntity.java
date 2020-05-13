package com.wuda.foundation.lang;

/**
 * 用于CRUD操作的实体类,data carrier class.查看{@link CRUDEntityState}的说明.
 * 一个实体类被用于CRUD操作时传递参数,一个很大的问题是每种操作不知道该设置哪些属性,
 * 比如用new关键字实例化对象后,该调用哪些属性的setter方法呢?因此为每种操作提供一个
 * 检测的方法.
 *
 * @author wuda
 * @see CRUDEntityState
 * @since 1.0.0
 */
public abstract class CRUDEntity extends BasicAttribute {

    protected CRUDEntityState crudState;

    /**
     * CRUD state.
     *
     * @return CRUD state
     */
    public CRUDEntityState CRUDState() {
        return crudState;
    }

    /**
     * 当实体用于{@link CRUDEntityState#CREATE}操作时,检查实体的有效性.
     */
    protected abstract void forCreateCheck();

    /**
     * 当实体用于{@link CRUDEntityState#UPDATE}操作时,检查实体的有效性.
     */
    protected abstract void forUpdateCheck();

    /**
     * 当实体用于{@link CRUDEntityState#READ}操作时,检查实体的有效性.
     */
    protected void forReadCheck() {
        // 一般应该很少约束
        checkCRUDState(CRUDEntityState.READ);
    }

    /**
     * 当实体用于{@link CRUDEntityState#DELETE}操作时,检查实体的有效性.
     */
    protected void forDeleteCheck() {
        // 一般会用主键ID,而不是实体作为参数去删除数据
        checkCRUDState(CRUDEntityState.DELETE);
    }

    /**
     * 检测是否期望的状态,如果不是将抛出异常.
     *
     * @param expect 期望的状态
     */
    protected void checkCRUDState(CRUDEntityState expect) {
        if (CRUDState() != expect) {
            throw new IllegalStateException("entity CRUD state 不是" + expect);
        }
    }

    /**
     * for update check,快捷方式.
     */
    protected void forUpdateCheckShortcut0() {
        checkCRUDState(CRUDEntityState.UPDATE);
        createUserIdMustNullCheck();
        createTimeMustNullCheck();
        lastModifyUserIdNonNullCheck();
        lastModifyTimeNonNullCheck();
    }

    /**
     * for update check,快捷方式.
     */
    protected void forCreateCheckShortcut0() {
        checkCRUDState(CRUDEntityState.CREATE);
        createTimeNonNullCheck();
        createUserIdNonNullCheck();
    }

    /**
     * {@link CRUDEntity}的builder.
     *
     * @author wuda
     * @see CRUDEntityState
     * @since 1.0.0
     */
    public abstract static class CRUDEntityBuilder<T extends CRUDEntity, B extends CRUDEntityBuilder<T, B>> extends BasicAttributeBuilder<T, B> {

        protected CRUDEntityState crudState;

        /**
         * 即将被构建的{@link CRUDEntity}的CRUD状态.
         *
         * @param crudState {@link CRUDEntityState}
         */
        protected CRUDEntityBuilder(CRUDEntityState crudState) {
            this.crudState = crudState;
        }

        /**
         * 在{@link #build()}方法调用之前执行.
         *
         * @param entity 即将被{@link #build()}方法返回的实例
         */
        protected void beforeBuild(T entity) {
            entity.crudState = crudState;
            CRUDEntityState crudState = entity.CRUDState();
            switch (crudState) {
                case CREATE:
                    entity.forCreateCheck();
                    break;
                case READ:
                    entity.forReadCheck();
                    break;
                case UPDATE:
                    entity.forUpdateCheck();
                    break;
                case DELETE:
                    entity.forDeleteCheck();
                    break;
                default:
                    throw new UnsupportedOperationException("entity CRUD state = " + crudState + ",不支持");
            }
        }
    }
}
