package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;
import com.wuda.foundation.lang.tree.IdPidEntryUtils;

import java.util.ArrayList;
import java.util.List;

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

    protected abstract CreateResult createTreeNodeDbOp(C createTreeNode, CreateMode createMode, Long opUserId);

    @Override
    public void updateNode(U updateTreeNode, Long opUserId) throws AlreadyExistsException {
        updateTreeNodeDbOp(updateTreeNode, opUserId);
    }

    protected abstract void updateTreeNodeDbOp(U updateTreeNode, Long opUserId) throws AlreadyExistsException;

    @Override
    public void deleteTreeNode(Long nodeId, Long opUserId) throws RelatedDataExists {
        int childCount = childCount(nodeId);
        if (childCount > 0) {
            throw new RelatedDataExists("节点ID = " + nodeId + ",还有下级");
        }
        deleteTreeNodeDbOp(nodeId, opUserId);
    }

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
}
