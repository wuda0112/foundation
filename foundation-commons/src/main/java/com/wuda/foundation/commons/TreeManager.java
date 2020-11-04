package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;

import java.util.List;

/**
 * 树形结构管理.
 * 很多术语参考<a href="http://www.cs.columbia.edu/~allen/S14/NOTES/trees.pdf">trees</a>
 *
 * @author wuda
 * @since 1.0.3
 */
public interface TreeManager {

    /**
     * 创建一个节点.
     *
     * @param createTreeNode 新增节点的参数
     * @param createMode     create mode
     * @param opUserId       操作者用户ID
     * @return 创建结果
     * @throws AlreadyExistsException       如果已经存在
     * @throws ParentNodeNotExistsException 父节点不存在
     */
    CreateResult createTreeNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) throws AlreadyExistsException, ParentNodeNotExistsException;

    /**
     * 更新节点.
     *
     * @param updateTreeNode 更新参数
     * @param opUserId       操作者用户ID
     * @throws AlreadyExistsException 如果更新节点的名称,并且和当前节点平级的节点中已经有该名称的节点
     */
    void updateNode(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException;

    /**
     * 删除节点.
     *
     * @param nodeId   准备删除的节点ID
     * @param opUserId 操作者用户ID
     * @throws RelatedDataExists 如果该节点下还有子节点存在
     */
    void deleteNode(Long nodeId, Long opUserId) throws RelatedDataExists;

    /**
     * 获取给定节点的所有后代.
     *
     * @param nodeId 节点ID
     * @return 所有后代的ID的集合
     */
    List<Long> getDescendants(Long nodeId);

    /**
     * 获取给定节点的所有祖先.
     *
     * @param nodeId 节点ID
     * @return 所有祖先的ID的集合
     */
    List<Long> getAncestors(Long nodeId);

    /**
     * 获取给定节点的所有子女(不包含孙子及更后的后代)节点的ID.
     *
     * @param nodeId 节点ID
     * @return 所有children的ID的集合
     */
    List<Long> getChildren(Long nodeId);

    /**
     * 获取节点信息.
     *
     * @param nodeId node id
     * @return 节点信息
     */
    DescribeTreeNode getTreeNode(Long nodeId);

    /**
     * 判断正在创建的节点是否root节点.
     *
     * @param createTreeNode 创建节点的参数
     * @return <code>true</code>-如果是
     */
    boolean isCreatingRootTreeNode(CreateTreeNode createTreeNode);

    /**
     * 判断节点是否root节点.
     *
     * @param describeTreeNode 节点
     * @return <code>true</code>-如果是
     */
    boolean isRootTreeNode(DescribeTreeNode describeTreeNode);

}
