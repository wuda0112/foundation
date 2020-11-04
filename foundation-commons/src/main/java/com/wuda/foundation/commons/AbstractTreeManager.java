package com.wuda.foundation.commons;

import com.wuda.foundation.lang.*;

import java.util.List;

public abstract class AbstractTreeManager implements TreeManager {
    @Override
    public CreateResult createTreeNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) throws AlreadyExistsException, ParentNodeNotExistsException {
        boolean isCreatingRootTreeNode = isCreatingRootTreeNode(createTreeNode);
        DescribeTreeNode parentTreeNode = null;
        if (!isCreatingRootTreeNode) {
            parentTreeNode = getTreeNode(createTreeNode.parentTreeNodeId);
            if (parentTreeNode == null) {
                throw new ParentNodeNotExistsException("parent tree node id = " + createTreeNode.parentTreeNodeId + ",不存在");
            }
        }
        supplementArg(createTreeNode, isCreatingRootTreeNode, parentTreeNode);
        CreateResult createResult = createNodeDbOp(createTreeNode, createMode, opUserId);
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
                rootTreeNodeId = parentTreeNode.getTreeNodeId();
            } else {
                rootTreeNodeId = parentTreeNode.getRootTreeNodeId();
            }
            depth = parentTreeNode.getDepth() + 1;
        } else {
            rootTreeNodeId = Constant.NOT_EXISTS_ID;
            depth = 1;
        }
        creating.setRootTreeNodeId(rootTreeNodeId);
        creating.setDepth(depth);
    }

    public abstract CreateResult createNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId);

    @Override
    public void updateNode(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException {
        updateNodeDbOp(updateTreeNode, opUserId);
    }

    public abstract void updateNodeDbOp(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException;

    @Override
    public void deleteNode(Long nodeId, Long opUserId) throws RelatedDataExists {
        deleteNodeDbOp(nodeId, opUserId);
    }

    protected abstract void deleteNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists;

    @Override
    public List<Long> getDescendants(Long nodeId) {
        return null;
    }

    @Override
    public List<Long> getAncestors(Long nodeId) {
        return null;
    }

    @Override
    public List<Long> getChildren(Long nodeId) {
        return null;
    }

    @Override
    public DescribeTreeNode getTreeNode(Long nodeId) {
        return null;
    }

    @Override
    public boolean isCreatingRootTreeNode(CreateTreeNode createTreeNode) {
        return false;
    }

    @Override
    public boolean isRootTreeNode(DescribeTreeNode describeTreeNode) {
        return false;
    }
}
