package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;

public abstract class AbstractGroupManager implements GroupManager {
    @Override
    public CreateResult createGroup(TreeManager treeManager, CreateGroup createGroup, CreateMode createMode, Long opUserId) throws AlreadyExistsException,ParentNodeNotExistsException {
        CreateTreeNode createTreeNode = createGroup.toCreateTreeNode();
        treeManager.createTreeNode(createTreeNode, createMode, opUserId);
        CreateResult createResult = createGroupDbOp(createGroup, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            // 正常应该不会来到这里,除非数据有问题,以防万一,比如没有使用数据库事物导致两个表的数据不一致
            throw new AlreadyExistsException("组中已经存在.数据有问题,因为既然可以在Tree中成功创建,到Group中却不能成功创建");
        }
        return createResult;
    }

    protected abstract CreateResult createGroupDbOp(CreateGroup createGroup, CreateMode createMode, Long opUserId);

    @Override
    public void deleteGroup(TreeManager treeManager, Long groupId, Long opUserId) throws RelatedDataExists {
        treeManager.deleteNode(groupId, opUserId);
        deleteGroupDbOp(groupId, opUserId);
    }

    protected abstract void deleteGroupDbOp(Long groupId, Long opUserId) throws RelatedDataExists;
}
