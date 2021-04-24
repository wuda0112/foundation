package com.wuda.foundation.core.security.impl;

import com.wuda.foundation.core.security.*;
import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionAction;
import com.wuda.foundation.jooq.code.generation.security.tables.records.PermissionActionRecord;
import com.wuda.foundation.jooq.code.generation.security.tables.records.PermissionAssignmentRecord;
import com.wuda.foundation.jooq.code.generation.security.tables.records.PermissionTargetRecord;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体之间转换类.
 *
 * @author wuda
 * @since 1.0.3
 */
class EntityConverter {

    static DescribePermissionTarget from(PermissionTargetRecord target) {
        if (target == null) {
            return null;
        }
        DescribePermissionTarget describePermissionTarget = new DescribePermissionTarget();
        describePermissionTarget.setId(target.getPermissionTargetId().longValue());
        describePermissionTarget.setCategoryId(target.getPermissionCategoryId().longValue());
        describePermissionTarget.setName(target.getName());
        describePermissionTarget.setType(target.getType().byteValue());
        IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(target.getReferencedType().intValue());
        LongIdentifier referencedIdentifier = new LongIdentifier(target.getReferencedIdentifier().longValue(), identifierType);
        describePermissionTarget.setReferencedIdentifier(referencedIdentifier);
        describePermissionTarget.setDescription(target.getDescription());
        return describePermissionTarget;
    }

    static DescribePermissionAction fromActionRecord(PermissionActionRecord record) {
        PermissionAction permissionAction = record.into(PermissionAction.class);
        return EntityConverter.fromAction(permissionAction);
    }

    static DescribePermissionAction fromAction(PermissionAction action) {
        DescribePermissionAction describePermissionAction = new DescribePermissionAction();
        describePermissionAction.setId(action.getPermissionActionId().longValue());
        describePermissionAction.setPermissionTargetId(action.getPermissionTargetId().longValue());
        describePermissionAction.setName(action.getName());
        IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(action.getReferencedType().intValue());
        LongIdentifier identifier = new LongIdentifier(action.getReferencedIdentifier().longValue(), identifierType);
        describePermissionAction.setDescription(action.getDescription());
        describePermissionAction.setReferencedIdentifier(identifier);
        return describePermissionAction;
    }

    static List<DescribePermissionAction> fromList(List<PermissionAction> actions) {
        if (actions == null || actions.isEmpty()) {
            return null;
        }
        List<DescribePermissionAction> list = new ArrayList<>(actions.size());
        for (PermissionAction action : actions) {
            DescribePermissionAction describePermissionAction = fromAction(action);
            list.add(describePermissionAction);
        }
        return list;
    }

    static DescribePermissionAssignment fromAssignmentRecord(PermissionAssignmentRecord record) {
        DescribePermissionAssignment descriptor = new DescribePermissionAssignment();
        descriptor.setId(record.getId().longValue());
        IdentifierType subjectType = IdentifierTypeRegistry.defaultRegistry.lookup(record.getSubjectType().intValue());
        Subject subject = new Subject(record.getSubjectIdentifier().longValue(), subjectType);
        descriptor.setSubject(subject);
        IdentifierType targetType = IdentifierTypeRegistry.defaultRegistry.lookup(record.getTargetType().intValue());
        Target target = new Target(record.getTargetIdentifier().longValue(), targetType);
        descriptor.setTarget(target);
        Action action = getAction(record);
        descriptor.setAction(action);
        descriptor.setEffect(PermissionEffect.parse(record.getEffect()));
        return descriptor;
    }

    static List<DescribePermissionAssignment> fromAssignmentRecords(List<PermissionAssignmentRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribePermissionAssignment> list = new ArrayList<>(records.size());
        for (PermissionAssignmentRecord record : records) {
            DescribePermissionAssignment describePermissionAssignment = fromAssignmentRecord(record);
            list.add(describePermissionAssignment);
        }
        return list;
    }

    static Action getAction(PermissionAssignmentRecord record) {
        IdentifierType actionType = IdentifierTypeRegistry.defaultRegistry.lookup(record.getActionType().intValue());
        return new Action(record.getActionIdentifier().longValue(), actionType);
    }
}
