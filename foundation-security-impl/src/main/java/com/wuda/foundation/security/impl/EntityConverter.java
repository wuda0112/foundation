package com.wuda.foundation.security.impl;

import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.DescribePermissionAction;
import com.wuda.foundation.security.DescribePermissionAssignment;
import com.wuda.foundation.security.DescribePermissionTarget;
import com.wuda.foundation.security.PermissionAssignmentCommand;
import com.wuda.foundation.security.Subject;
import com.wuda.foundation.security.impl.jooq.generation.tables.pojos.PermissionAction;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionActionRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionAssignmentRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionTargetRecord;

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
        descriptor.setTargetId(record.getPersissionTargetId().longValue());
        descriptor.setActionId(record.getPermissionActionId().longValue());
        descriptor.setCommand(PermissionAssignmentCommand.getByCommand(record.getCommand()));
        return descriptor;
    }
}
