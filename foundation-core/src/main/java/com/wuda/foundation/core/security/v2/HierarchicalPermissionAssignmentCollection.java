package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.lang.tree.TreeNode;

/**
 * <p>
 * 权限分配到一个主体(比如用户组)上,而这个主体(比如用户组)本身是有层次结构的,
 * 因此权限的分配信息也按主体的层次结构组织.
 * <p>
 * 比如有如下的用户组
 * <blockquote>
 * <pre>
 *         a
 *         - - b
 *         - - - - c
 *         d
 *         - - e
 *         - - - - f
 *     </pre>
 * </blockquote>
 * 然后为用户组分配权限
 * <blockquote>
 * <pre>
 *         a
 *         - - b
 *         - - - - can read/write file a
 *         - - - - can write file b
 *         - - - - c
 *         d
 *         - - can read/write file a
 *         - - can read file b
 *         - - e
 *         - - - - f
 *     </pre>
 * </blockquote>
 * 我们可以知道
 * <ul>
 * <li>为用户组<strong>b</strong>分配了两个权限,即可以读/写文件a和可以写文件b</li>
 * <li>为用户组<strong>d</strong>分配了两个权限,即可以读/写文件a和可以读文件b</li>
 * </ul>
 * <strong>如果权限可以继承</strong>,则按照用户组的结构组织权限的分配时,
 * 就可以很容易的知道
 * <ul>用户组<strong>c</strong>继承了用户组<strong>b</strong>的权限</ul>
 * <ul>用户组<strong>e,f</strong>继承了用户组<strong>a</strong>的权限</ul>
 * </p>
 * 根据以上的描述,现在来介绍下这个类命名的特点
 * <ul>
 * <li>Hierarchical,因为主体是有层次结构的,按主体组织的权限分配信息也具有层次结构</li>
 * <li>Collection,为主体分配的权限的集合</li>
 * </ul>
 * 这个很像文件系统中Directory/File的关系,在这里Subject就好比是Directory,权限分配信息就好比是File.
 *
 * @author wuda
 */
public class HierarchicalPermissionAssignmentCollection extends PermissionAssignmentCollection implements TreeNode<Long> {

    private HierarchicalPermissionSubject subject;

    /**
     * 构建实例,用于存储{@link DescribePermissionAssignment permission assignment}.
     *
     * @param subject         权限的主体对象,比如用户
     * @param initialCapacity 该集合的初始化容量
     */
    public HierarchicalPermissionAssignmentCollection(HierarchicalPermissionSubject subject, int initialCapacity) {
        super(initialCapacity);
        this.subject = subject;
    }

    /**
     * 获取subject.集合中的所有权限分配的信息都属于该Subject,
     * 并且层次结构也是按该Subject的层次结构组织.
     *
     * @return subject
     */
    public HierarchicalPermissionSubject getSubject() {
        return subject;
    }

    @Override
    public String getName() {
        return subject.getName();
    }

    @Override
    public Long getId() {
        return subject.getId();
    }

    @Override
    public Long getPid() {
        return subject.getPid();
    }
}
