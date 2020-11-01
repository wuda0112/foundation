package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;
import com.wuda.foundation.lang.tree.Tree;

import java.util.List;

/**
 * tree_node表管理.
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
     * @throws AlreadyExistsException 如果已经存在
     */
    CreateResult createNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) throws AlreadyExistsException;

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
}
