package com.wuda.foundation.lang;

/**
 * id校验器.在数据保存到数据库之前,校验数据的唯一id,有几个好处
 * <ul>
 * <li>不使用数据库自增,使用自定义生成的id,迁移数据时,已经生成的id可以一直使用,保证了关联到该id的数据的正确性</li>
 * <li>如果想预留一些id作为保留使用,则必须有一套机制检测,这里就是</li>
 * <li>由于保存到数据库的id都是经过检验的,可以不使用{@link Long}这种包装类型了,可以使用原始数据类型,
 * 由于很多ORM框架在新增和修改数据时,是以是否为<code>null</code>来判断是否新增和更新该字段,因此还不能使用元素数据类型</li>
 * </ul>
 */
public class IdValidator {

    /**
     * 禁止实例化.
     */
    protected IdValidator() {

    }

    /**
     * id必须大于系统内置最小值{@link Constant#ID_MIN}.
     *
     * @param id 记录的唯一id
     */
    public static void greaterThanMin(long id) {
        if (id <= Constant.ID_MIN) {
            throw new IllegalStateException("记录的ID不能小于" + Constant.ID_MIN);
        }
    }

    /**
     * id不能等于0.
     *
     * @param id 记录的唯一id
     */
    public static void notEqualsZero(long id) {
        if (id == 0) {
            throw new IllegalStateException("记录的ID不能等于" + 0);
        }
    }

    /**
     * {@link #notEqualsZero(long)}和{@link #greaterThanMin(long)}的快捷方式.
     *
     * @param id 记录的唯一id
     */
    public static void notEqualsZeroAndGreaterThanMin(long id) {
        notEqualsZero(id);
        greaterThanMin(id);
    }
}
