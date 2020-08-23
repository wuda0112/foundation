package com.wuda.foundation.security.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.security.AbstractPermissionGrantManager;
import com.wuda.foundation.security.Subject;
import com.wuda.foundation.security.SubjectPermission;
import com.wuda.foundation.security.SubjectPermissionRelationshipCommand;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionActionRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.SubjectPermissionRelationshipRecord;
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
import static com.wuda.foundation.security.impl.jooq.generation.tables.SubjectPermissionRelationship.SUBJECT_PERMISSION_RELATIONSHIP;

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
        List<SubjectPermissionRelationshipRecord> records = getSubjectPermissionRelationship(subject, targetId);
        if (records == null || records.size() == 0) {
            SubjectPermissionRelationshipRecord record = subjectPermissionRelationshipRecordForInsert(subject, targetId, Constant.NOT_EXISTS_ID, SubjectPermissionRelationshipCommand.GRANT, opUserId);
            insert(dataSource, SUBJECT_PERMISSION_RELATIONSHIP, record);
        } else if (records.size() == 1 && records.get(0).getPermissionActionId().longValue() == Constant.NOT_EXISTS_ID) {
            // 已经有这样的记录,do nothing
        } else { // 应该是分配的这个target的多个action
            deleteSubjectPermission(subject, targetId, null);
            SubjectPermissionRelationshipRecord record = subjectPermissionRelationshipRecordForInsert(subject, targetId, Constant.NOT_EXISTS_ID, SubjectPermissionRelationshipCommand.GRANT, opUserId);
            insert(dataSource, SUBJECT_PERMISSION_RELATIONSHIP, record);
        }
    }

    private SubjectPermissionRelationshipRecord subjectPermissionRelationshipRecordForInsert(Subject subject,
                                                                                             Long targetId,
                                                                                             Long actionId,
                                                                                             SubjectPermissionRelationshipCommand command,
                                                                                             Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new SubjectPermissionRelationshipRecord(
                ULong.valueOf(FoundationContext.getLongKeyGenerator().next()),
                UByte.valueOf(subject.getType().getCode()),
                ULong.valueOf(subject.getValue()),
                ULong.valueOf(targetId),
                ULong.valueOf(actionId),
                command.getCommand(),
                now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    protected List<SubjectPermissionRelationshipRecord> getSubjectPermissionRelationship(Subject subject, Long targetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep<SubjectPermissionRelationshipRecord> selectWhereStep = dslContext.selectFrom(SUBJECT_PERMISSION_RELATIONSHIP)
                .where(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
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
        SubjectPermissionRelationshipRecord subjectPermissionRelationshipRecord = querySubjectPermissionRelationshipRecord(subject, targetId, actionId);
        if (subjectPermissionRelationshipRecord == null) {
            subjectPermissionRelationshipRecord = querySubjectPermissionRelationshipRecord(subject, targetId, Constant.NOT_EXISTS_ID);
        }
        if (subjectPermissionRelationshipRecord != null
                && subjectPermissionRelationshipRecord.getCommand().equals(SubjectPermissionRelationshipCommand.GRANT.getCommand())) {
            return;
        }
        if (subjectPermissionRelationshipRecord != null) {
            deleteSubjectPermission(subject, targetId, actionId);
        }
        SubjectPermissionRelationshipRecord record = subjectPermissionRelationshipRecordForInsert(subject, targetId, actionId, SubjectPermissionRelationshipCommand.GRANT, opUserId);
        insert(dataSource, SUBJECT_PERMISSION_RELATIONSHIP, record);
    }

    protected void deleteSubjectPermission(Subject subject, Long targetId, Long actionId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        UpdateConditionStep<SubjectPermissionRelationshipRecord> updateConditionStep = dslContext.update(SUBJECT_PERMISSION_RELATIONSHIP)
                .set(SUBJECT_PERMISSION_RELATIONSHIP.IS_DELETED, SUBJECT_PERMISSION_RELATIONSHIP.ID)
                .where(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)));
        if (actionId != null) {
            updateConditionStep.and(SUBJECT_PERMISSION_RELATIONSHIP.PERMISSION_ACTION_ID.eq(ULong.valueOf(actionId)));
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

    protected SubjectPermissionRelationshipRecord querySubjectPermissionRelationshipRecord(Subject subject, Long targetId, Long actionId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(SUBJECT_PERMISSION_RELATIONSHIP)
                .where(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.PERMISSION_ACTION_ID.eq(ULong.valueOf(actionId)))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetchOne();
    }

    protected List<SubjectPermissionRelationshipRecord> querySubjectPermissionRelationshipRecords(Subject subject, Long targetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep<SubjectPermissionRelationshipRecord> selectConditionStep = dslContext.selectFrom(SUBJECT_PERMISSION_RELATIONSHIP)
                .where(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(SUBJECT_PERMISSION_RELATIONSHIP.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())));
        if (targetId != null) {
            selectConditionStep.and(SUBJECT_PERMISSION_RELATIONSHIP.PERSISSION_TARGET_ID.eq(ULong.valueOf(targetId)));
        }
        selectConditionStep.and(SUBJECT_PERMISSION_RELATIONSHIP.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return selectConditionStep.fetch();
    }

    @Override
    protected void revokeTargetDbOp(Subject subject, Set<Long> targetIdSet, Long opUserId) {
        for (Long targetId : targetIdSet) {
            revokeTargetDbOp(subject, targetId, opUserId);
        }
    }

    protected void revokeTargetDbOp(Subject subject, Long targetId, Long opUserId) {
        deleteSubjectPermission(subject, targetId, null);
    }

    @Override
    protected void revokeActionDbOp(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId) {
        checkActionBelongToTarget(targetId, actionIdSet);
        for (long actionId : actionIdSet) {
            revokeActionDbOp(subject, targetId, actionId, opUserId);
        }
    }

    protected void revokeActionDbOp(Subject subject, Long targetId, Long actionId, Long opUserId) {
        SubjectPermissionRelationshipRecord existsSubjectPermissionRelationship = querySubjectPermissionRelationshipRecord(subject, targetId, actionId);
        if (existsSubjectPermissionRelationship != null
                && existsSubjectPermissionRelationship.getCommand().equals(SubjectPermissionRelationshipCommand.REVOKE.getCommand())) {
            // 已经revoke
            return;
        }
        if (existsSubjectPermissionRelationship != null) {
            deleteSubjectPermission(subject, targetId, actionId);
        }
        SubjectPermissionRelationshipRecord subjectPermissionRelationshipRecord = subjectPermissionRelationshipRecordForInsert(subject, targetId, actionId, SubjectPermissionRelationshipCommand.REVOKE, opUserId);
        insert(dataSource, SUBJECT_PERMISSION_RELATIONSHIP, subjectPermissionRelationshipRecord);
    }

    @Override
    protected List<SubjectPermission> getPermissionDbOp(Subject subject) {
        List<SubjectPermissionRelationshipRecord> records = querySubjectPermissionRelationshipRecords(subject, null);
        return null;
    }
}
