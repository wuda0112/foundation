/**
 * 电商系统,商品属性表和功能设计,实现
 * 定义一种通用的属性体系,可以表示任何实体的属性,同时也像数据库列一样,可以为属性指定数据类型.
 * 在电商系统中,每个商品都有多个属性,并且这些属性不能提前设定,它们以无模式的key/value形式存在,
 * 我们可以简单的只保存key/value，也可以把这种需求抽象成更通用，功能更强大的属性体系,我们这里的实现
 * 有几个特点
 * <ul>
 * <li>可以表示任何实体的属性.比如商品的属性,店铺的属性等等</li>
 * <li>像定义MySQL table column一样,可以定义属性的数据类型.</li>
 * <li>数据类型以插件模式开发,默认实现了MySQL相关的数据类型,使用者也可以实现自己的数据类型</li>
 * <li>为属性定义type,假设一件衣服有【颜色=红色】,【尺寸=大】两个属性,在页面上显示时,我们可以直接显示这样的文字,但是如果【颜色】这个属性不显示文字,
 * 而是显示真实的颜色呢?这个时候,假设【颜色】这个属性设置type=color,属性值设置为#FFFFFF,那么UI程序根据约定好的type的定义就知道这个属性应该显示
 * 真实的颜色,而不是字面量#FFFFFF了.再比如属性是链接类型时,前端界面就可以以Url的形式显示,再比如是图片类型时,前端界面就显示图片,等等.所以属性有了type,
 * 设值和显示时体验就更好了,</li>
 * <li>换个角度思考,可以当作系统变量,环境变量等功能使用<只要把属性的owner设置成系统即可.
 * 这种用法下,在编程时,我们是明确要拿着这个property key使用的,为了不在代码中出现大量的魔法值,我们可以用{@link com.wuda.foundation.commons.property.PropertyKeyNaming}
 * 提前把property key用强类型(最好是枚举)固定下来,在代码中使用时,引用就方便了/li>
 * </ul>
 * <p>
 * 如何表示任意实体的属性?通过{@link com.wuda.foundation.commons.property.CreatePropertyKey#getOwner()}可以指定这个属性
 * 属于任何owner,当owner type为商品时,owner identifier就表示商品的id;当owner type为店铺时,owner identifier就表示店铺的id,等等,
 * 通过这种方式就可以表示任意实体.
 * <p>
 * 如何像Column definition一样为属性设置definition?通过{@link com.wuda.foundation.commons.property.CreatePropertyKeyDefinition}为属性设置定义,
 * 比如设置属性的{@link com.wuda.foundation.lang.datatype.DataType}数据类型,也可以不为属性设置definition,这时属性值就可以是任何字符串.
 *
 * 健壮的API.通常,基于数据库的web应用,在操作数据库时,就是代码自动生成的dao,mapper等这些API,没有做任何完整性检查等.我们这里再更改数据库时,对参数
 * 做了完整的校验,然后业务逻辑也做了校验,保证数据的完整性.比如{@link com.wuda.foundation.lang.CreateMode}就可以选择先检查数据是否存在,如果不存在
 * 才插入数据库,{@link com.wuda.foundation.lang.CreateResult}返回值表明到底有没有新增数据,如果没有新增,则已经存在的数据的ID将被返回,调用者可以根据
 * 返回值就知道到底有没有新增记录.
 */
package com.wuda.foundation.commons.property;