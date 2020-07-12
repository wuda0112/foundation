package com.wuda.foundation.commons.property;

/**
 * 属性的类型，比如最常用的就是“字面量”类型；比如该属性表示图片，属性值则保存图片的链接；
 * 比如该属性表示颜色，因为在一些应用中，可以使用调色盘选取颜色，或者在显示时，可以显示颜色，
 * 而不是白色这样的纯文本.有了这些信息才能更好的管理property.
 *
 * @author wuda
 */
public interface PropertyKeyType {

    /**
     * 获取code.
     *
     * @return code
     */
    int getCode();

    /**
     * 描述信息.
     *
     * @return 描述
     */
    String getDescription();
}
