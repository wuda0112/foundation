package com.wuda.foundation.security.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.security.AbstractPermissionGrantManager;
import com.wuda.foundation.security.PermissionAssignmentCommand;
import com.wuda.foundation.security.Subject;
import com.wuda.foundation.security.DescribePermissionAssignment;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionActionRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionAssignmentRecord;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.UpdateConditionStep;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAction.PERMISSION_ACTION;
import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAssignment.PERMISSION_ASSIGNMENT;

public class PermissionGrantManagerImpl extends AbstractPermissionGrantManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void grantTargetDbOp(Subject subject, Set<Long> targetIdSet, Long opUserId) {
        for (Long targetId : targetIdSet) {
            grantTargetDbOp(subject, targetId, opUserId);
        }
    }

    protected void grantTargetDbOp(Subject subject, Long targetId, Long opUserId) {
        List<PermissionAssignmentRecord> records = getPermissionAssignment(subject, targetId);
        if (records == null || records.size() == 0) {
            PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, targetId, Constant.NOT_EXISTS_ID, PermissionAssignmentCommand.GRANT, opUserId);
            insert(dataSource, PERMISSION_ASSIGNMENT, record);
        } else if (records.size() == 1 && records.get(0).getPermissionActionId().longValue() == Constant.NOT_EXISTS_ID) {
            // 已经有这样的记录,do nothing
        } else { // 应该是分配的这个target的多个action
            deletePermissionAssignment(subject, targetId, null);
            PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, targetId, Constant.NOT_EXISTS_ID, PermissionAssignmentCommand.GRANT, opUserId);
            insert(dataSource, PERMISSION_ASSIGNMENT, record);
        }
    }

    private PermissionAssignmentRecord permissionAssignmentRecordForInsert(Subject subject,
                                                                           Long targetId,
                                                                           Long actionId,
                                                                           PermissionAssignmentCommand command,
                                                                           Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionAssignmentRecord(
                ULong.valueOf(FoundationContext.getLongKeyGenerator().next()),
                UByte.valueOf(subject.getType().getCode()),
                ULong.valueOf(subject.getValue()),
                ULong.valueOf(targetId),
                ULong.valueOf(actionId),
                command.getCommand(),
                now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    protected List<PermissionAssignmentRecord> getPermissionAssignment(Subject subject, Long targetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep<PermissionAssignmentRecord> selectWhereStep = dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return selectWhereStep.fetch();
    }

    @Override
    protected void grantActionDbOp(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId) {
        checkActionBelongToTarget(targetId, actionIdSet);
        for (Long actionId : actionIdSet) {
            grantActionDbOp(subject, targetId, actionId, opUserId);
        }
    }

    protected void grantActionDbOp(Subject subject, Long targetId, Long actionId, Long opUserId) {
        PermissionAssignmentRecord permissionAssignmentRecord = queryPermissionAssignmentRecord(subject, targetId, actionId);
        if (permissionAssignmentRecord == null) {
            permissionAssignmentRecord = queryPermissionAssignmentRecord(subject, targetId, Constant.NOT_EXISTS_ID);
        }
        if (permissionAssignmentRecord != null
                && permissionAssignmentRecord.getCommand().equals(PermissionAssignmentCommand.GRANT.getCommand())) {
            return;
        }
        if (permissionAssignmentRecord != null) {
            deletePermissionAssignment(subject, targetId, actionId);
        }
        PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, targetId, actionId, PermissionAssignmentCommand.GRANT, opUserId);
        insert(dataSource, PERMISSION_ASSIGNMENT, record);
    }

    protected void deletePermissionAssignment(Subject subject, Long targetId, Long actionId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        UpdateConditionStep<PermissionAssignmentRecord> updateConditionStep = dslContext.update(PERMISSION_ASSIGNMENT)
                .set(PERMISSION_ASSIGNMENT.IS_DELETED, PERMISSION_ASSIGNMENT.ID)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)));
        if (actionId != null) {
            updateConditionStep.and(PERMISSION_ASSIGNMENT.PERMISSION_ACTION_ID.eq(ULong.valueOf(actionId)));
        }
        updateConditionStep.execute();
    }

    private void checkActionBelongToTarget(Long targetId, Set<Long> actionIdSet) {
        List<PermissionActionRecord> list = queryPermissionActionRecord(actionIdSet);
        for (PermissionActionRecord record : list) {
            if (record.getPermissionTargetId().longValue() != targetId) {
                throw new IllegalStateException("action id = " + record.getPermissionActionId().longValue() + ",不属于target id = " + targetId + "所表示的target");
            }
        }
    }

    protected List<PermissionActionRecord> queryPermissionActionRecord(Set<Long> actionIdSet) {
        Set<ULong> set = new HashSet<>(actionIdSet.size());
        for (Long actionId : actionIdSet) {
            set.add(ULong.valueOf(actionId));
        }
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_ACTION_ID.in(set))
                .and(PERMISSION_ACTION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetch();
    }

    protected PermissionAssignmentRecord queryPermissionAssignmentRecord(Subject subject, Long targetId, Long actionId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)))
                .and(PERMISSION_ASSIGNMENT.PERMISSION_ACTION_ID.eq(ULong.valueOf(actionId)))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetchOne();
    }

    protected List<PermissionAssignmentRecord> queryPermissionAssignmentRecords(Subject subject, Long targetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep<PermissionAssignmentRecord> selectConditionStep = dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())));
        if (targetId != null) {
            selectConditionStep.and(PERMISSION_ASSIGNMENT.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)));
        }
        selectConditionStep.and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return selectConditionStep.fetch();
    }

    @Override
    protected void revokeTargetDbOp(Subject subject, Set<Long> targetIdSet, Long opUserId) {
        for (Long targetId : targetIdSet) {
            revokeTargetDbOp(subject, targetId, opUserId);
        }
    }

    protected void revokeTargetDbOp(Subject subject, Long targetId, Long opUserId) {
        deletePermissionAssignment(subject, targetId, null);
    }

    @Override
    protected void revokeActionDbOp(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId) {
        checkActionBelongToTarget(targetId, actionIdSet);
        for (long actionId : actionIdSet) {
            revokeActionDbOp(subject, targetId, actionId, opUserId);
        }
    }

    protected void revokeActionDbOp(Subject subject, Long targetId, Long actionId, Long opUserId) {
        PermissionAssignmentRecord existsPermissionAssignment = queryPermissionAssignmentRecord(subject, targetId, actionId);
        if (existsPermissionAssignment != null
                && existsPermissionAssignment.getCommand().equals(PermissionAssignmentCommand.REVOKE.getCommand())) {
            // 已经revoke
            return;
        }
        if (existsPermissionAssignment != null) {
            deletePermissionAssignment(subject, targetId, actionId);
        }
        PermissionAssignmentRecord permissionAssignmentRecord = permissionAssignmentRecordForInsert(subject, targetId, actionId, PermissionAssignmentCommand.REVOKE, opUserId);
        insert(dataSource, PERMISSION_ASSIGNMENT, permissionAssignmentRecord);
    }

    @Override
    protected List<DescribePermissionAssignment> getPermissionDbOp(Subject subject) {
        List<PermissionAssignmentRecord> records = queryPermissionAssignmentRecords(subject, null);
        return null;
    }
}
