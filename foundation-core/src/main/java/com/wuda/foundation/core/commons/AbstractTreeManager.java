package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.*;
import com.wuda.foundation.lang.tree.IdPidEntryTreeBuilder;
import com.wuda.foundation.lang.tree.IdPidEntryUtils;
import com.wuda.foundation.lang.tree.MappedTree;
import com.wuda.foundation.lang.tree.Tree;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用树形管理的抽象实现类.
 *
 * @param <C> 用于创建节点的参数的类型
 * @param <U> 用于更新节点的参数的类型
 * @param <D> 用于描述节点的类型
 * @author wuda
 * @since 1.0.3
 */
public abstract class AbstractTreeManager<C extends CreateTreeNode, U extends UpdateTreeNode, D extends DescribeTreeNode> implements TreeManager<C, U, D> {

    @Override
    public CreateResult createTreeNode(C createTreeNode, CreateMode createMode, Long opUserId) throws AlreadyExistsException, ParentNodeNotExistsException {
        boolean isCreatingRootTreeNode = isCreatingRootTreeNode(createTreeNode);
        DescribeTreeNode parentTreeNode = null;
        if (!isCreatingRootTreeNode) {
            parentTreeNode = getTreeNode(createTreeNode.parentId);
            if (parentTreeNode == null) {
                throw new ParentNodeNotExistsException("parent tree node id = " + createTreeNode.parentId + ",不存在");
            }
        }
        boolean nameExists = checkNameExists(createTreeNode.parentId, createTreeNode.name);
        if (nameExists) {
            throw new AlreadyExistsException("parent tree node id = " + createTreeNode.parentId + ",已经存在名称为 " + createTreeNode.name + " 的子节点");
        }
        supplementArg(createTreeNode, isCreatingRootTreeNode, parentTreeNode);
        CreateResult createResult = createTreeNodeDbOp(createTreeNode, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("树中已经存在");
        }
        return createResult;
    }

    /**
     * 为正在创建的节点补充参数,比如补充root tree node id.
     *
     * @param creating               正在创建的节点
     * @param isCreatingRootTreeNode 正在创建的节点是否root node
     * @param parentTreeNode         正在创建的节点的父节点
     */
    private void supplementArg(CreateTreeNode creating, boolean isCreatingRootTreeNode, DescribeTreeNode parentTreeNode) {
        long rootTreeNodeId;
        int depth;
        if (!isCreatingRootTreeNode) {
            if (isRootTreeNode(parentTreeNode)) {
                rootTreeNodeId = parentTreeNode.getId();
            } else {
                rootTreeNodeId = parentTreeNode.getRootId();
            }
            depth = parentTreeNode.getDepth() + 1;
        } else {
            rootTreeNodeId = Constant.NOT_EXISTS_ID;
            depth = 1;
        }
        creating.setRootId(rootTreeNodeId);
        creating.setDepth(depth);
    }

    /**
     * 新增节点实际执行的数据库操作.
     *
     * @param createTreeNode 创建节点的参数
     * @param createMode     create mode
     * @param opUserId       操作人用户ID
     * @return 新建的节点的ID
     */
    protected abstract CreateResult createTreeNodeDbOp(C createTreeNode, CreateMode createMode, Long opUserId);

    @Override
    public void updateNode(U updateTreeNode, Long opUserId) throws AlreadyExistsException {
        D existsTreeNode = getTreeNode(updateTreeNode.id);
        if (StringUtils.equals(existsTreeNode.name, updateTreeNode.name)) {
            // 更新了名称,需要检查名称是否已经存在
            boolean nameExists = checkNameExists(existsTreeNode.getParentId(), updateTreeNode.name);
            if (nameExists) {
                throw new AlreadyExistsException("parent tree node id = " + existsTreeNode.parentId + ",已经存在名称为 " + updateTreeNode.name + " 的子节点");
            }
        }
        updateTreeNodeDbOp(updateTreeNode, opUserId);
    }

    /**
     * 更新节点实际执行的数据库操作.
     *
     * @param opUserId       操作人用户ID
     * @param updateTreeNode 更新节点的参数
     */
    protected abstract void updateTreeNodeDbOp(U updateTreeNode, Long opUserId);

    @Override
    public void deleteTreeNode(Long nodeId, Long opUserId) throws RelatedDataExists {
        int childCount = childCount(nodeId);
        if (childCount > 0) {
            throw new RelatedDataExists("节点ID = " + nodeId + ",还有下级");
        }
        deleteTreeNodeDbOp(nodeId, opUserId);
    }

    /**
     * 删除节点实际执行的数据库操作.
     *
     * @param opUserId 操作人用户ID
     * @param nodeId   节点ID
     */
    protected abstract void deleteTreeNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists;

    @Override
    public List<D> getDescendant(Long nodeId) {
        if (nodeId == null) {
            return null;
        }
        D treeNode = getTreeNode(nodeId);
        if (treeNode == null) {
            return null;
        }
        List<D> fullNodes = getDescendantOfRoot(treeNode.getRootId());
        List<D> descendant = new ArrayList<>();
        IdPidEntryUtils.getDescendant(nodeId, fullNodes, descendant);
        return descendant;
    }

    @Override
    public List<D> getAncestor(Long nodeId) {
        if (nodeId == null) {
            return null;
        }
        D treeNode = getTreeNode(nodeId);
        if (treeNode == null) {
            return null;
        }
        List<D> fullNodes = getDescendantOfRoot(treeNode.getRootId());
        List<D> ancestor = new ArrayList<>();
        IdPidEntryUtils.getAncestor(nodeId, fullNodes, ancestor);
        return ancestor;
    }

    @Override
    public boolean isCreatingRootTreeNode(CreateTreeNode createTreeNode) {
        return createTreeNode.parentId.equals(Constant.NOT_EXISTS_ID);
    }

    @Override
    public boolean isRootTreeNode(DescribeTreeNode describeTreeNode) {
        return describeTreeNode.getParentId().equals(Constant.NOT_EXISTS_ID);
    }

    @Override
    public Tree<Long, D> getTree(Long nodeId) {
        D node = getTreeNode(nodeId);
        D root = null;
        Long rootNodeId = null;
        if (isRootTreeNode(node)) {
            rootNodeId = nodeId;
            root = node;
        } else {
            rootNodeId = node.rootId;
            root = getTreeNode(rootNodeId);
        }
        List<D> descendants = getDescendantOfRoot(rootNodeId);
        IdPidEntryTreeBuilder<Long, D> builder = new IdPidEntryTreeBuilder<>();
        Tree<Long, D> tree = new MappedTree<>(root);
        builder.add(tree, descendants);
        return tree;
    }
}
