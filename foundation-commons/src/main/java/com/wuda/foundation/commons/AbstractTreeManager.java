package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;
import com.wuda.foundation.lang.tree.IdPidEntryTreeBuilder;
import com.wuda.foundation.lang.tree.MappedTree;
import com.wuda.foundation.lang.tree.Tree;

import java.util.List;

public abstract class AbstractTreeManager implements TreeManager {
    @Override
    public CreateResult createNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        return createNodeDbOp(createTreeNode, createMode, opUserId);
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
    public Tree<Long, DescribeTreeNode> tree() {
        Tree<Long, DescribeTreeNode> tree = new MappedTree<>(DescribeTreeNode.root);
        List<DescribeTreeNode> allNodes = getAllNodes();
        if (allNodes == null || allNodes.isEmpty()) {
            return tree;
        }
        IdPidEntryTreeBuilder<Long, DescribeTreeNode> treeBuilder = new IdPidEntryTreeBuilder<>();
        treeBuilder.add(tree, allNodes);
        return tree;
    }

    @Override
    public List<DescribeTreeNode> getAllNodes() {
        return getAllNodesDbOp();
    }

    protected abstract List<DescribeTreeNode> getAllNodesDbOp();
}
