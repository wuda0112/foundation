package com.wuda.foundation.lang;

import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
import com.wuda.foundation.lang.tree.RootTreeNodeSelectCondition;
import com.wuda.foundation.lang.tree.TreeNode;

/**
 * 在项目级别内的Context.
 *
 * @author wuda
 * @since 1.0.0
 */
public class FoundationContext {

    private static KeyGenerator<Long> longKeyGenerator = new KeyGeneratorSnowflake(1);
    /**
     * 根节点选择条件.
     */
    private static RootTreeNodeSelectCondition rootTreeNodeSelectCondition = (treeNode -> treeNode.getPid().equals(Constant.NOT_EXISTS_ID));

    /**
     * 设置key generator.
     *
     * @param longKeyGenerator key generator,用于生成long类型的key
     */
    public static void setLongKeyGenerator(KeyGenerator<Long> longKeyGenerator) {
        FoundationContext.longKeyGenerator = longKeyGenerator;
    }

    /**
     * 获取long类型的key generator.
     *
     * @return key generator,用于生成long类型的key
     */
    public static KeyGenerator<Long> getLongKeyGenerator() {
        return longKeyGenerator;
    }

    /**
     * 检查是否根节点.
     *
     * @param treeNode 树节点
     * @return <code>true</code>-如果是
     */
    public static boolean isRootTreeNode(TreeNode<Long> treeNode) {
        return rootTreeNodeSelectCondition.isRoot(treeNode);
    }
}
