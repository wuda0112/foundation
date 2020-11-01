package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;

public abstract class AbstractTreeManager implements TreeManager {
    @Override
    public CreateResult createNode(CreateTreeNode createTreeNode, CreateMode createMode, Long opUserId) throws AlreadyExistsException{
        CreateResult createResult = createNodeDbOp(createTreeNode, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("树中已经存在");
        }
        return createResult;
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
}
