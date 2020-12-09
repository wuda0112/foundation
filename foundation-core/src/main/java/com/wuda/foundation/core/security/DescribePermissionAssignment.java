package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * describe permission assignment.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DescribePermissionAssignment {

    /**
     * 数据库的ID.
     */
    private Long id;

    /**
     * subject.
     */
    private Subject subject;
    /**
     * target.
     */
    private Target target;
    /**
     * action.
     */
    private Action action;
    /**
     * allowOrDeny.
     */
    private AllowOrDeny allowOrDeny;

    /**
     * 是否分配了整个{@link Target},可以是allow,也可以是deny.比如文件A分配给了用户A,并且没有指定任何文件的操作,
     * 则表示把整个文件A都分配给了用户A,所表示的意思就是用户A拥有了文件A的所有操作.
     *
     * @param assignment assignment记录
     * @return <code>true</code>-如果整个{@link Target}分配给了{@link Subject}
     */
    public static boolean assignWholeTarget(DescribePermissionAssignment assignment) {
        return Action.isVirtual(assignment.action);
    }

}
