package com.wuda.foundation.lang;

/**
 * 用于CRUD操作的实体类,data carrier class.查看{@link CRUDState}的说明.
 * 一个实体类被用于CRUD操作时传递参数,一个很大的问题是每种操作不知道该设置哪些属性,
 * 比如用new关键字实例化对象后,该调用哪些属性的setter方法呢?因此为每种操作提供一个
 * 检测的方法.
 *
 * @author wuda
 * @see CRUDState
 * @since 1.0.0
 */
public abstract class CRUDEntity extends BasicAttribute {

    protected CRUDState crudState;

    /**
     * CRUD state.
     *
     * @return CRUD state
     */
    public CRUDState CRUDState() {
        return crudState;
    }

    /**
     * 当实体用于{@link CRUDState#CREATE}操作时,检查实体的有效性.
     */
    protected abstract void forCreateCheck();

    /**
     * 当实体用于{@link CRUDState#UPDATE}操作时,检查实体的有效性.
     */
    protected abstract void forUpdateCheck();

    /**
     * 当实体用于{@link CRUDState#READ}操作时,检查实体的有效性.
     */
    protected void forReadCheck() {
        // 一般应该很少约束
        checkCRUDState(CRUDState.READ);
    }

    /**
     * 当实体用于{@link CRUDState#DELETE}操作时,检查实体的有效性.
     */
    protected void forDeleteCheck() {
        // 一般会用主键ID,而不是实体作为参数去删除数据
        checkCRUDState(CRUDState.DELETE);
    }

    /**
     * 检测是否期望的状态,如果不是将抛出异常.
     *
     * @param expected 期望的状态
     */
    protected void checkCRUDState(CRUDState expected) {
        if (CRUDState() != expected) {
            throw new IllegalStateException("entity CRUD state 不是" + expected);
        }
    }

    /**
     * for update check,快捷方式.
     */
    protected void forUpdateCheckShortcut0() {
        checkCRUDState(CRUDState.UPDATE);
        createUserIdMustNullCheck();
        createTimeMustNullCheck();
        lastModifyUserIdNonNullCheck();
        lastModifyTimeNonNullCheck();
    }

    /**
     * for update check,快捷方式.
     */
    protected void forCreateCheckShortcut0() {
        checkCRUDState(CRUDState.CREATE);
        createTimeNonNullCheck();
        createUserIdNonNullCheck();
    }

    /**
     * {@link CRUDEntity}的builder.
     *
     * @author wuda
     * @see CRUDState
     * @since 1.0.0
     */
    public abstract static class CRUDEntityBuilder<T extends CRUDEntity, B extends CRUDEntityBuilder<T, B>> extends BasicAttributeBuilder<T, B> {

        protected CRUDState crudState;

        /**
         * 即将被构建的{@link CRUDEntity}的CRUD状态.
         *
         * @param crudState {@link CRUDState}
         */
        protected CRUDEntityBuilder(CRUDState crudState) {
            this.crudState = crudState;
        }

        /**
         * 在{@link #build()}方法调用之前执行.
         *
         * @param entity 即将被{@link #build()}方法返回的实例
         */
        protected void beforeBuild(T entity) {
            entity.crudState = crudState;
            CRUDState crudState = entity.CRUDState();
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
