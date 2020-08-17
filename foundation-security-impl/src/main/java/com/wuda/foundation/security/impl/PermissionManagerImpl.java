package com.wuda.foundation.security.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.AbstractPermissionManager;
import com.wuda.foundation.security.CreatePermissionAction;
import com.wuda.foundation.security.CreatePermissionTarget;
import com.wuda.foundation.security.DescribePermission;
import com.wuda.foundation.security.DescribePermissionAction;
import com.wuda.foundation.security.DescribePermissionTarget;
import com.wuda.foundation.security.impl.jooq.generation.tables.pojos.PermissionAction;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionActionRecord;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionTargetRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.RecordMapper;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAction.PERMISSION_ACTION;
import static com.wuda.foundation.security.impl.jooq.generation.tables.PermissionTarget.PERMISSION_TARGET;

public class PermissionManagerImpl extends AbstractPermissionManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected CreateResult createPermissionTargetDbOp(CreatePermissionTarget target, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PERMISSION_TARGET.PERMISSION_TARGET_ID)
                .from(PERMISSION_TARGET)
                .where(PERMISSION_TARGET.PERMISSION_CATEGORY_ID.eq(ULong.valueOf(target.getCategoryId())))
                .and(PERMISSION_TARGET.NAME.eq(target.getName()))
                .and(PERMISSION_TARGET.REFERENCED_TYPE.eq(UByte.valueOf(target.getReferencedIdentifier().getType().getCode())))
                .and(PERMISSION_TARGET.REFERENCED_IDENTIFIER.eq(ULong.valueOf(target.getReferencedIdentifier().getValue())))
                .and(PERMISSION_TARGET.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        PermissionTargetRecord permissionTargetRecord = permissionTargetRecordForInsert(target, opUserId);
        return insertIfNotExists(dataSource, PERMISSION_TARGET, permissionTargetRecord, existsRecordSelector);
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
    protected CreateResult createPermissionActionDbOp(CreatePermissionAction action, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PERMISSION_ACTION.PERMISSION_ACTION_ID)
                .from(PERMISSION_ACTION)
                .where(PERMISSION_ACTION.PERMISSION_TARGET_ID.eq(ULong.valueOf(action.getPermissionTargetId())))
                .and(PERMISSION_ACTION.NAME.eq(action.getName()))
                .and(PERMISSION_ACTION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        PermissionActionRecord permissionActionRecord = permissionActionRecordForInsert(action, opUserId);
        return insertIfNotExists(dataSource, PERMISSION_ACTION, permissionActionRecord, existsRecordSelector);
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
        long targetId = createPermissionTargetDbOp(target, opUserId).getRecordId();
        createPermissionAction(actions, opUserId);
        return targetId;
    }

    @Override
    protected void createPermissionActionDbOp(Set<CreatePermissionAction> actions, Long opUserId) {
        for (CreatePermissionAction createPermissionAction : actions) {
            createPermissionAction(createPermissionAction, opUserId);
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
    protected void updatePermissionTargetDbOp(Long permissionTargetId, String name, String description, Long opUserId) {

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
    protected void updatePermissionActionDbOp(Long permissionActionId, String action, String description, Long opUserId) {

    }

    @Override
    protected DescribePermission getPermissionDbOp(Long permissionTargetId) {
        DescribePermissionTarget target = getPermissionTargetByIdDbOp(permissionTargetId);
        List<DescribePermissionAction> actions = getPermissionActionByTargetDbOp(permissionTargetId);
        return new DescribePermission(target, actions);
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
}
