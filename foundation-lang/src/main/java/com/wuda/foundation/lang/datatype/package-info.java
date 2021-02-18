/**
 * 在Java语言中有int,long,char等数据类型,在MySQL中有varchar,tinyint等数据类型,类似的,我们这里也定义数据类型.
 *
 * <h1>用途</h1>
 * 在定义Property(key/value Pair)时,我们可以指定Property Value的数据类型
 *
 * <h1>使用已有的数据类型</h1>
 * 一种简单的方式就是使用已有的数据类型,比如: MySQL的varchar,tinyint等数据类型,因为这些数据类型我们都比较熟悉,
 * 免去了很多定义,我们需要做的就是按照MySQL对这些数据类型的定义,完成数据检测即可.比如已经实现的Data Type有{@link com.wuda.foundation.lang.datatype.mysql.MySQLDataType#INT},{@link com.wuda.foundation.lang.datatype.mysql.MySQLDataType#VARCHAR}等等.
 * <h1>自定义数据类型</h1>
 * 我们也可以自己设计一套数据类型,只要输入的数据满足我们定义的规则即可.比如我们定义一个数据类型,名称叫做"K",
 * 唯一的规则就是要求输入的数据中必须包含字母"K",为了满足这个规则,我们只需要实现{@link com.wuda.foundation.lang.datatype.DataTypeHandler}这个接口,然后在{@link com.wuda.foundation.lang.datatype.DataTypeHandler#validate(com.wuda.foundation.lang.datatype.DataDefinition, java.lang.String)}中校验输入数据是否满足数据类型的定义,如果满足定义,
 * 则使用{@link com.wuda.foundation.lang.datatype.DataTypeHandler#parseValue(java.lang.String)}将文本类型的输入数据
 * 转换成实际的数据类型.
 */
package com.wuda.foundation.lang.datatype;