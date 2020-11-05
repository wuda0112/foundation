package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.RelatedDataExists;

public abstract class AbstractGroupManager implements GroupManager {
    @Override
    public CreateResult createGroup(CreateGroup createGroup, CreateMode createMode, Long opUserId) throws AlreadyExistsException, ParentNodeNotExistsException {
        CreateResult createResult = createGroupDbOp(createGroup, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("创建的组已经存在");
        }
        return createResult;
    }

    protected abstract CreateResult createGroupDbOp(CreateGroup createGroup, CreateMode createMode, Long opUserId);

    @Override
    public void deleteGroup(Long groupId, Long opUserId) throws RelatedDataExists {
        deleteGroupDbOp(groupId, opUserId);
    }

    protected abstract void deleteGroupDbOp(Long groupId, Long opUserId) throws RelatedDataExists;
}
