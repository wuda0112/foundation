package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

public abstract class AbstractGroupManager implements GroupManager {
    @Override
    public CreateResult createGroup(CreateGroup createGroup, CreateMode createMode, Long opUserId) throws AlreadyExistsException {
        CreateResult createResult = createGroupCoreDbOp(createGroup, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("组中已经存在");
        }
        return createResult;
    }

    protected abstract CreateResult createGroupCoreDbOp(CreateGroup createGroup, CreateMode createMode, Long opUserId);
}
