package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractGroupManager;
import com.wuda.foundation.commons.CreateGroup;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.GroupRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.RelatedDataExists;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;

import java.time.LocalDateTime;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.Group.GROUP;

public class GroupManagerImpl extends AbstractGroupManager implements JooqCommonDbOp {

    @Override
    protected CreateResult createGroupDbOp(CreateGroup createGroup, CreateMode createMode, Long opUserId) {
        GroupRecord record = groupRecordForInsert(createGroup, opUserId);
        SelectConditionStep<Record1<ULong>> selectCondition = selectCondition(createGroup.getGroupId(), createGroup.getParentGroupId());
        return insertDispatcher(JooqContext.getDataSource(), createMode, GROUP, record, selectCondition);
    }

    @Override
    protected void deleteGroupDbOp(Long groupId, Long opUserId) throws RelatedDataExists {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource());
        dslContext.update(GROUP)
                .set(GROUP.IS_DELETED, GROUP.GROUP_ID)
                .where(GROUP.GROUP_ID.eq(ULong.valueOf(groupId)))
                .execute();
    }

    private GroupRecord groupRecordForInsert(CreateGroup createGroup, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new GroupRecord(ULong.valueOf(createGroup.getGroupId()),
                ULong.valueOf(createGroup.getParentGroupId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    /**
     * 用于唯一性查询的条件.
     *
     * @param parentGroupId 父节点
     * @param groupId       子节点
     * @return {@link SelectConditionStep}
     */
    private SelectConditionStep<Record1<ULong>> selectCondition(Long groupId, Long parentGroupId) {
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        return DSL.using(configuration)
                .select(GROUP.GROUP_ID)
                .from(GROUP)
                .where(GROUP.PARENT_GROUP_ID.eq(ULong.valueOf(parentGroupId)))
                .and(GROUP.GROUP_ID.eq(ULong.valueOf(groupId)))
                .and(GROUP.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }
}
