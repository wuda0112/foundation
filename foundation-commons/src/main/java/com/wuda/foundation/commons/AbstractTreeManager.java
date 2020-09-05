package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

public abstract class AbstractTreeManager implements TreeManager {
    @Override
    public CreateResult createNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) {
        return createNodeDbOp(createTreeNode, createMode, opUserId);
    }

    public abstract CreateResult createNodeDbOp(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId);

    @Override
    public void updateNode(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException{
        updateNodeDbOp(updateTreeNode, opUserId);
    }

    public abstract void updateNodeDbOp(UpdateTreeNode updateTreeNode, Long opUserId) throws AlreadyExistsException;
}
