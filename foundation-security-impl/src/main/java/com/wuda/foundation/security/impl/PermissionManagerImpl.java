package com.wuda.foundation.security.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.*;
import com.wuda.foundation.security.impl.jooq.generation.tables.pojos.PermissionAction;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionActionRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionRoleRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionTargetRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAction.PERMISSION_ACTION;
import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionRole.PERMISSION_ROLE;
import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionTarget.PERMISSION_TARGET;

public class PermissionManagerImpl extends AbstractPermissionManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected CreateResult createPermissionTargetDbOp(CreatePermissionTarget target, CreateMode createMode, Long opUserId) {
        SelectConditionStep<Record1<ULong>> existsRecordSelector = uniqueTargetSelector(target.getCategoryId(), target.getReferencedIdentifier(), target.getName());
        PermissionTargetRecord permissionTargetRecord = permissionTargetRecordForInsert(target, opUserId);
        return insertDispatcher(dataSource, createMode, PERMISSION_TARGET, permissionTargetRecord, existsRecordSelector);
    }

    private SelectConditionStep<Record1<ULong>> uniqueTargetSelector(long categoryId, LongIdentifier referencedIdentifier, String name) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        return DSL.using(configuration)
                .select(PERMISSION_TARGET.PERMISSION_TARGET_ID)
                .from(PERMISSION_TARGET)
                .where(PERMISSION_TARGET.PERMISSION_CATEGORY_ID.eq(ULong.valueOf(categoryId)))
                .and(PERMISSION_TARGET.NAME.eq(name))
                .and(PERMISSION_TARGET.REFERENCED_TYPE.eq(UByte.valueOf(referencedIdentifier.getType().getCode())))
                .and(PERMISSION_TARGET.REFERENCED_IDENTIFIER.eq(ULong.valueOf(referencedIdentifier.getValue())))
                .and(PERMISSION_TARGET.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private PermissionTargetRecord permissionTargetRecordForInsert(CreatePermissionTarget target, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionTargetRecord(ULong.valueOf(target.getId()),
                ULong.valueOf(target.getCategoryId()),
                target.getName(),
                UByte.valueOf(target.getType()),
                UByte.valueOf(target.getReferencedIdentifier().getType().getCode()),
                ULong.valueOf(target.getReferencedIdentifier().getValue()),
                target.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    @Override
    protected CreateResult createPermissionActionDbOp(CreatePermissionAction action, CreateMode createMode, Long opUserId) {

        SelectConditionStep<Record1<ULong>> existsRecordSelector = uniqueActionSelector(action.getPermissionTargetId(), action.getName());
        PermissionActionRecord permissionActionRecord = permissionActionRecordForInsert(action, opUserId);
        return insertDispatcher(dataSource, createMode, PERMISSION_ACTION, permissionActionRecord, existsRecordSelector);
    }

    private SelectConditionStep<Record1<ULong>> uniqueActionSelector(long targetId, String name) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        return DSL.using(configuration)
                .select(PERMISSION_ACTION.PERMISSION_ACTION_ID)
                .from(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_TARGET_ID.eq(ULong.valueOf(targetId)))
                .and(PERMISSION_ACTION.NAME.eq(name))
                .and(PERMISSION_ACTION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private PermissionActionRecord permissionActionRecordForInsert(CreatePermissionAction action, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionActionRecord(ULong.valueOf(action.getId()),
                ULong.valueOf(action.getPermissionTargetId()),
                action.getName(),
                action.getDescription(),
                UByte.valueOf(action.getReferencedIdentifier().getType().getCode()),
                ULong.valueOf(action.getReferencedIdentifier().getValue()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    @Override
    protected long createPermissionDbOp(CreatePermissionTarget target, Set<CreatePermissionAction> actions, Long opUserId) {
        long targetId = createPermissionTargetDbOp(target, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId).getRecordId();
        createPermissionAction(actions, opUserId);
        return targetId;
    }

    @Override
    protected void createPermissionActionDbOp(Set<CreatePermissionAction> actions, Long opUserId) {
        for (CreatePermissionAction createPermissionAction : actions) {
            createPermissionAction(createPermissionAction, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        }
    }

    @Override
    protected void deleteActionByTargetDbOp(Long permissionTargetId, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        dslContext.deleteFrom(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_TARGET_ID.eq(ULong.valueOf(permissionTargetId)))
                .execute();
    }

    @Override
    protected void deleteActionDbOp(Long permissionActionId, Long opUserId) {
        PermissionActionRecord action = new PermissionActionRecord();
        action.setPermissionActionId(ULong.valueOf(permissionActionId));
        action.attach(JooqContext.getConfiguration(dataSource));
        action.delete();
    }

    @Override
    protected void deleteTargetDbOp(Long permissionTargetId, Long opUserId) {
        PermissionTargetRecord target = new PermissionTargetRecord();
        target.setPermissionTargetId(ULong.valueOf(permissionTargetId));
        target.attach(JooqContext.getConfiguration(dataSource));
        target.delete();

        deleteActionByTargetDbOp(permissionTargetId, opUserId);
    }

    @Override
    protected void updatePermissionTargetDbOp(Long targetId, String name, String description, Long opUserId) throws AlreadyExistsException {
        DescribePermissionTarget describePermissionTarget = getPermissionTargetByIdDbOp(targetId);
        if (describePermissionTarget == null) {
            return;
        } else if (describePermissionTarget.getName().equals(name)) {
            return;
        }
        LongIdentifier referencedIdentifier = describePermissionTarget.getReferencedIdentifier();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = uniqueTargetSelector(describePermissionTarget.getCategoryId(), referencedIdentifier, name);
        if (existsRecordSelector.fetchOne() != null) {
            throw new AlreadyExistsException("referenced type = " + referencedIdentifier.getType().getCode() + ",referenced id = " + referencedIdentifier.getValue() + ",已经有name = " + name + ",这样的target");
        } else {
            UpdatePermissionTarget updatePermissionTarget = new UpdatePermissionTarget.Builder()
                    .setId(targetId)
                    .setName(name)
                    .setDescription(description)
                    .build();
            updatePermissionTargetDbOp(updatePermissionTarget, opUserId);
        }
    }

    @Override
    protected DescribePermissionTarget getPermissionTargetByIdDbOp(Long permissionTargetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        PermissionTargetRecord target = dslContext.selectFrom(PERMISSION_TARGET)
                .where(PERMISSION_TARGET.PERMISSION_TARGET_ID.eq(ULong.valueOf(permissionTargetId)))
                .fetchOne();
        return from(target);
    }

    @Override
    protected DescribePermissionAction getPermissionActionByIdDbOp(Long permissionActionId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        PermissionActionRecord action = dslContext.selectFrom(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_ACTION_ID.eq(ULong.valueOf(permissionActionId)))
                .fetchOne();
        PermissionAction permissionAction = action.into(PermissionAction.class);
        return from(permissionAction);
    }

    @Override
    protected List<DescribePermissionAction> getPermissionActionByTargetDbOp(Long permissionTargetId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        RecordMapper<PermissionActionRecord, PermissionAction> mapper = configuration.recordMapperProvider().provide(PERMISSION_ACTION.recordType(), PermissionAction.class);
        List<PermissionAction> actions = dslContext.selectFrom(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_TARGET_ID.eq(ULong.valueOf(permissionTargetId)))
                .fetch(mapper);
        return fromList(actions);
    }

    @Override
    protected void updatePermissionActionDbOp(Long actionId, String name, String description, Long opUserId) throws AlreadyExistsException {
        DescribePermissionAction describePermissionAction = getPermissionActionByIdDbOp(actionId);
        if (describePermissionAction == null) {
            return;
        } else if (describePermissionAction.getName().equals(name)) {
            return;
        }
        long targetId = describePermissionAction.getPermissionTargetId();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = uniqueActionSelector(targetId, name);
        if (existsRecordSelector.fetchOne() != null) {
            throw new AlreadyExistsException("permission target id = " + targetId + ",已经有name = " + name + ",这样的action");
        } else {
            UpdatePermissionAction updatePermissionAction = new UpdatePermissionAction.Builder()
                    .setId(actionId)
                    .setName(name)
                    .setDescription(description)
                    .build();
            updatePermissionActionDbOp(updatePermissionAction, opUserId);
        }
    }

    protected void updatePermissionActionDbOp(UpdatePermissionAction updatePermissionAction, Long opUserId) {
        PermissionActionRecord record = new PermissionActionRecord();
        record.setPermissionActionId(ULong.valueOf(updatePermissionAction.getId()));
        record.setName(updatePermissionAction.getName());
        record.setDescription(updatePermissionAction.getDescription());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    protected void updatePermissionTargetDbOp(UpdatePermissionTarget updatePermissionTarget, Long opUserId) {
        PermissionTargetRecord record = new PermissionTargetRecord();
        record.setPermissionTargetId(ULong.valueOf(updatePermissionTarget.getId()));
        record.setName(updatePermissionTarget.getName());
        record.setDescription(updatePermissionTarget.getDescription());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected DescribePermission getPermissionDbOp(Long permissionTargetId) {
        DescribePermissionTarget target = getPermissionTargetByIdDbOp(permissionTargetId);
        List<DescribePermissionAction> actions = getPermissionActionByTargetDbOp(permissionTargetId);
        return new DescribePermission(target, actions);
    }

    @Override
    protected List<DescribePermission> getPermissionsDbOp(Subject subject) {
        return null;
    }

    @Override
    protected CreateResult createPermissionRoleDbOp(CreatePermissionRoleRequest request, CreateMode createMode, Long opUserId) {
        PermissionRoleRecord record = permissionRoleRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = permissionRoleUniqueCondition(request.getType(), request.getName());
        return insertDispatcher(JooqContext.getDataSource(), createMode, PERMISSION_ROLE, record, existsRecordSelector);
    }

    @Override
    protected void updatePermissionRoleDbOp(UpdatePermissionRoleRequest request, Long opUserId) throws AlreadyExistsException {
        PermissionRoleRecord record = permissionRoleRecordForUpdate(request, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected DescribePermissionRole getPermissionRoleByIdDbOp(Long id) {
        PermissionRoleRecord record = JooqContext.getOrCreateDSLContext().selectFrom(PERMISSION_ROLE)
                .where(PERMISSION_ROLE.PERMISSION_ROLE_ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return copyRoleFrom(record);
    }

    private DescribePermissionTarget from(PermissionTargetRecord target) {
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

    private DescribePermissionAction from(PermissionAction action) {
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

    private List<DescribePermissionAction> fromList(List<PermissionAction> actions) {
        if (actions == null || actions.isEmpty()) {
            return null;
        }
        List<DescribePermissionAction> list = new ArrayList<>(actions.size());
        for (PermissionAction action : actions) {
            DescribePermissionAction describePermissionAction = from(action);
            list.add(describePermissionAction);
        }
        return list;
    }

    private PermissionRoleRecord permissionRoleRecordForInsert(CreatePermissionRoleRequest request, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionRoleRecord(ULong.valueOf(request.getId()),
                UByte.valueOf(request.getType().getCode()),
                request.getName(),
                request.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private PermissionRoleRecord permissionRoleRecordForUpdate(UpdatePermissionRoleRequest request, Long opUserId) {
        PermissionRoleRecord record = new PermissionRoleRecord();
        record.setPermissionRoleId(ULong.valueOf(request.getId()));
        record.setName(request.getName());
        record.setDescription(request.getDescription());
        return record;
    }

    private DescribePermissionRole copyRoleFrom(PermissionRoleRecord record) {
        DescribePermissionRole describe = new DescribePermissionRole();
        describe.setId(record.getPermissionRoleId().longValue());
        PermissionRoleType type = IdentifierTypeRegistry.defaultRegistry.lookup(record.getType().intValue());
        describe.setType(type);
        describe.setName(record.getName());
        describe.setDescription(record.getDescription());
        return describe;
    }

    private SelectConditionStep<Record1<ULong>> permissionRoleUniqueCondition(PermissionRoleType type, String name) {
        Configuration configuration = JooqContext.getConfiguration();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PERMISSION_ROLE.PERMISSION_ROLE_ID)
                .from(PERMISSION_ROLE)
                .where(PERMISSION_ROLE.TYPE.eq(UByte.valueOf(type.getCode())))
                .and(PERMISSION_ROLE.NAME.eq(name))
                .and(PERMISSION_ROLE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return existsRecordSelector;
    }

}
