package com.wuda.foundation.security.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.security.*;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionAssignmentRecord;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.UpdateConditionStep;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAssignment.PERMISSION_ASSIGNMENT;

public class PermissionGrantManagerImpl extends AbstractPermissionGrantManager implements JooqCommonDbOp {

    @Override
    protected void createAssignmentDbOp(Subject subject, Set<Target> targetSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        for (Target target : targetSet) {
            createAssignmentDbOp(subject, target, inclusionOrExclusion, opUserId);
        }
    }

    protected void createAssignmentDbOp(Subject subject, Target target, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        if (subjectTargetAssigned(subject, target, inclusionOrExclusion)) {
            return;
        }
        List<PermissionAssignmentRecord> records = queryPermissionAssignmentRecords(subject, target);
        if (records != null && records.size() > 0) {
            // 之前已经建立过关联
            deletePermissionAssignment(subject, target, null);
        }
        PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, target, Action.fake(), inclusionOrExclusion, opUserId);
        insert(JooqContext.getDataSource(), PERMISSION_ASSIGNMENT, record);
    }

    private PermissionAssignmentRecord permissionAssignmentRecordForInsert(Subject subject,
                                                                           Target target,
                                                                           Action action,
                                                                           InclusionOrExclusion inclusionOrExclusion,
                                                                           Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionAssignmentRecord(
                ULong.valueOf(FoundationContext.getLongKeyGenerator().next()),
                UByte.valueOf(subject.getType().getCode()),
                ULong.valueOf(subject.getValue()),
                UShort.valueOf(target.getType().getCode()),
                ULong.valueOf(target.getValue()),
                UShort.valueOf(action.getType().getCode()),
                ULong.valueOf(action.getValue()),
                InclusionOrExclusion.inclusion(inclusionOrExclusion),
                now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    protected List<PermissionAssignmentRecord> queryPermissionAssignmentRecords(Subject subject, Target target) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        SelectConditionStep<PermissionAssignmentRecord> selectWhereStep = dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        if (target != null) {
            selectWhereStep.and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                    .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())));
        }
        return selectWhereStep.fetch();
    }

    @Override
    protected void createAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        for (Action action : actionSet) {
            Objects.requireNonNull(action);
            createAssignmentDbOp(subject, target, action, inclusionOrExclusion, opUserId);
        }
    }

    protected void createAssignmentDbOp(Subject subject, Target target, Action action, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        PermissionAssignmentRecord permissionAssignmentRecord = queryPermissionAssignmentRecord(subject, target, action);
        if (permissionAssignmentRecord != null
                && InclusionOrExclusion.equals(inclusionOrExclusion, permissionAssignmentRecord.getInclusion())) {
            // 已经分配过
            return;
        }
        if (permissionAssignmentRecord != null) {
            deletePermissionAssignment(subject, target, action);
        }
        PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, target, action, inclusionOrExclusion, opUserId);
        insert(JooqContext.getDataSource(), PERMISSION_ASSIGNMENT, record);
    }

    protected void deletePermissionAssignment(Subject subject, Target target, Action action) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        UpdateConditionStep<PermissionAssignmentRecord> updateConditionStep = dslContext.update(PERMISSION_ASSIGNMENT)
                .set(PERMISSION_ASSIGNMENT.IS_DELETED, PERMISSION_ASSIGNMENT.ID)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())));
        if (action != null) {
            updateConditionStep.and(PERMISSION_ASSIGNMENT.ACTION_TYPE.eq(UShort.valueOf(action.getType().getCode())))
                    .and(PERMISSION_ASSIGNMENT.ACTION_IDENTIFIER.eq(ULong.valueOf(action.getValue())));
        }
        updateConditionStep.execute();
    }

    protected PermissionAssignmentRecord queryPermissionAssignmentRecord(Subject subject, Target target, Action action) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        return dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())))
                .and(PERMISSION_ASSIGNMENT.ACTION_TYPE.eq(UShort.valueOf(action.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.ACTION_IDENTIFIER.eq(ULong.valueOf(action.getValue())))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(notDeleted()))
                .fetchOne();
    }

    @Override
    protected void clearAssignmentDbOp(Subject subject, Set<Target> targetSet, Long opUserId) {
        for (Target target : targetSet) {
            deletePermissionAssignment(subject, target, null);
        }
    }

    @Override
    protected void clearAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, Long opUserId) {
        for (Action action : actionSet) {
            deletePermissionAssignment(subject, target, action);
        }
    }

    @Override
    protected List<DescribePermission> getPermissionsDbOp(Subject subject) {
        List<PermissionAssignmentRecord> permissionAssignmentRecords = queryPermissionAssignmentRecords(subject, null);
        List<DescribePermissionAssignment> describePermissionAssignments = EntityConverter.fromAssignmentRecords(permissionAssignmentRecords);
        return DescribePermissionAssignment.sameSubjectCalculate(describePermissionAssignments);
    }

    @Override
    protected List<DescribePermission> getPermissionsDbOp(List<Subject> subjects) {
        return null;
    }

    @Override
    protected boolean subjectTargetAssignedDbOp(Subject subject, Target target, InclusionOrExclusion inclusionOrExclusion) {
        List<PermissionAssignmentRecord> records = queryPermissionAssignmentRecords(subject, target);
        if (records.size() == 1) {
            PermissionAssignmentRecord record = records.get(0);
            return EntityConverter.getAction(record).equals(Action.fake())
                    && InclusionOrExclusion.equals(inclusionOrExclusion, record.getInclusion());
        }
        return false;
    }
}
