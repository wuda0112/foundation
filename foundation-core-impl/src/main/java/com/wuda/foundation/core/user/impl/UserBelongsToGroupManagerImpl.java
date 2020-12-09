package com.wuda.foundation.core.user.impl;

import com.wuda.foundation.core.commons.DescribeMenuItem;
import com.wuda.foundation.core.commons.MenuItemManager;
import com.wuda.foundation.core.security.*;
import com.wuda.foundation.core.user.AbstractUserBelongsToGroupManager;
import com.wuda.foundation.core.user.CreateUserBelongsToGroupCoreRequest;
import com.wuda.foundation.core.user.CreateUserBelongsToGroupGeneralRequest;
import com.wuda.foundation.core.user.CreateUserBelongsToGroupRoleRequest;
import com.wuda.foundation.core.user.DescribeUserBelongsToGroupCore;
import com.wuda.foundation.core.user.DescribeUserBelongsToGroupGeneral;
import com.wuda.foundation.core.user.DescribeUserBelongsToGroupRole;
import com.wuda.foundation.core.user.RemoveUserFromGroupRequest;
import com.wuda.foundation.core.user.RemoveUsersRoleFromGroupRequest;
import com.wuda.foundation.core.user.UpdateUserBelongsToGroupGeneralRequest;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupCoreRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupGeneralRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupRoleRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuda.foundation.jooq.code.generation.user.Tables.USER_BELONGS_TO_GROUP_GENERAL;
import static com.wuda.foundation.jooq.code.generation.user.Tables.USER_BELONGS_TO_GROUP_ROLE;
import static com.wuda.foundation.jooq.code.generation.user.tables.UserBelongsToGroupCore.USER_BELONGS_TO_GROUP_CORE;

public class UserBelongsToGroupManagerImpl extends AbstractUserBelongsToGroupManager implements JooqCommonDbOp {

    private PermissionGrantManager permissionGrantManager;

    private PermissionManager permissionManager;

    private MenuItemManager menuItemManager;

    public void setPermissionGrantManager(PermissionGrantManager permissionGrantManager) {
        this.permissionGrantManager = permissionGrantManager;
    }

    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public void setMenuItemManager(MenuItemManager menuItemManager) {
        this.menuItemManager = menuItemManager;
    }

    @Override
    protected CreateResult createUserBelongsToGroupCoreDbOp(CreateUserBelongsToGroupCoreRequest request, CreateMode createMode, Long opUserId) {
        UserBelongsToGroupCoreRecord record = userBelongsToGroupCoreRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = userBelongsToGroupCoreCondition(request.getUserId(), request.getGroup());
        return insertDispatcher(JooqContext.getDataSource(), createMode, USER_BELONGS_TO_GROUP_CORE, record, existsRecordSelector);
    }

    @Override
    protected void removeUserFromGroupDbOp(RemoveUserFromGroupRequest request, Long opUserId) {
        LongIdentifier group = request.getGroup();
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(USER_BELONGS_TO_GROUP_CORE)
                .set(USER_BELONGS_TO_GROUP_CORE.IS_DELETED, USER_BELONGS_TO_GROUP_CORE.USER_BELONGS_TO_GROUP_CORE_ID)
                .where(USER_BELONGS_TO_GROUP_CORE.USER_ID.eq(ULong.valueOf(request.getUserId())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .execute();
    }

    @Override
    protected DescribeUserBelongsToGroupCore getUserBelongsToGroupCoreDbOp(Long id) {
        UserBelongsToGroupCoreRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_CORE)
                .where(USER_BELONGS_TO_GROUP_CORE.USER_BELONGS_TO_GROUP_CORE_ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return copyFrom(record);
    }

    @Override
    protected Long getUserBelongsToGroupIdDbOp(Long userId, LongIdentifier group) {
        UserBelongsToGroupCoreRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_CORE)
                .where(USER_BELONGS_TO_GROUP_CORE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .and(USER_BELONGS_TO_GROUP_CORE.IS_DELETED.eq(notDeleted()))
                .fetchOne();
        if (record == null) {
            return null;
        }
        return record.getUserBelongsToGroupId().longValue();
    }

    @Override
    protected CreateResult createUserBelongsToGroupGeneralDbOp(CreateUserBelongsToGroupGeneralRequest request, CreateMode createMode, Long opUserId) {
        UserBelongsToGroupGeneralRecord record = userBelongsToGroupGeneralRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = userBelongsToGroupGeneralCondition(request.getUserBelongsToGroupId());
        return insertDispatcher(JooqContext.getDataSource(), createMode, USER_BELONGS_TO_GROUP_GENERAL, record, existsRecordSelector);
    }

    @Override
    protected void updateUserBelongsToGroupGeneralDbOp(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        UserBelongsToGroupGeneralRecord record = userBelongsToGroupGeneralRecordForUpdate(request, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneralDbOp(Long id) {
        UserBelongsToGroupGeneralRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_BELONGS_TO_GROUP_GENERAL_ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return copyFrom(record);
    }

    @Override
    protected CreateResult createUserBelongsToGroupRoleDbOp(CreateUserBelongsToGroupRoleRequest request, CreateMode createMode, Long opUserId) {
        UserBelongsToGroupRoleRecord record = userBelongsToGroupRoleRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = userBelongsToGroupRoleCondition(request.getUserBelongsToGroupId(), request.getPermissionRoleId());
        return insertDispatcher(JooqContext.getDataSource(), createMode, USER_BELONGS_TO_GROUP_ROLE, record, existsRecordSelector);
    }

    @Override
    protected void removeUsersRoleFromGroupDbOp(RemoveUsersRoleFromGroupRequest request, Long opUserId) {
        LongIdentifier group = request.getGroup();
        Long userBelongsGroupId = getUserBelongsToGroupId(request.getUserId(), group);
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(USER_BELONGS_TO_GROUP_ROLE)
                .set(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED, USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ROLE_ID)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ID.eq(ULong.valueOf(userBelongsGroupId)))
                .execute();
    }

    @Override
    protected DescribeUserBelongsToGroupRole getUserBelongsToGroupRoleDbOp(Long id) {
        UserBelongsToGroupRoleRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_ROLE)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ROLE_ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return copyFrom(record);
    }

    @Override
    protected List<LongIdentifier> getGroupsDbOp(Long userId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record2<UShort, ULong>> results = dslContext.select(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE, USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER)
                .from(USER_BELONGS_TO_GROUP_CORE)
                .where(USER_BELONGS_TO_GROUP_CORE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_CORE.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (results == null || results.isEmpty()) {
            return null;
        }
        List<LongIdentifier> groups = new ArrayList<>(results.size());
        results.forEach((result -> {
            long groupId = result.get(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER).longValue();
            int _groupType = result.get(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE).intValue();
            IdentifierType groupType = IdentifierTypeRegistry.defaultRegistry.lookup(_groupType);
            LongIdentifier group = new LongIdentifier(groupId, groupType);
            groups.add(group);
        }));
        return groups;
    }

    @Override
    protected List<Long> getMembersDbOp(LongIdentifier group) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record1<ULong>> results = dslContext.select(USER_BELONGS_TO_GROUP_CORE.USER_ID)
                .from(USER_BELONGS_TO_GROUP_CORE)
                .where(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .and(USER_BELONGS_TO_GROUP_CORE.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (results == null || results.isEmpty()) {
            return null;
        }
        List<Long> userIds = new ArrayList<>(results.size());
        results.forEach((result -> {
            long userId = result.get(USER_BELONGS_TO_GROUP_CORE.USER_ID).longValue();
            userIds.add(userId);
        }));
        return userIds;
    }

    @Override
    protected List<DescribePermissionAssignment> getPermissionsFromRoleDbOp(Long userId, LongIdentifier group) {
        List<DescribePermissionRole> roles = getRoles(userId, group);
        List<Subject> subjects = DescribePermissionRole.toSubjects(roles);
        if (subjects == null || subjects.isEmpty()) {
            return null;
        }
        return permissionGrantManager.getPermissions(subjects);
    }

    @Override
    protected List<DescribePermissionRole> getRolesDbOp(Long userId, LongIdentifier group) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record1<ULong>> result = dslContext.select(USER_BELONGS_TO_GROUP_ROLE.PERMISSION_ROLE_ID)
                .from(USER_BELONGS_TO_GROUP_CORE)
                .leftJoin(USER_BELONGS_TO_GROUP_ROLE).on(USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ID.eq(USER_BELONGS_TO_GROUP_CORE.USER_BELONGS_TO_GROUP_ID))
                .where(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .and(USER_BELONGS_TO_GROUP_CORE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_CORE.IS_DELETED.eq(notDeleted()))
                .and(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (result == null || result.isEmpty()) {
            return null;
        }
        List<Long> roleIds = result.stream().map(Record1::value1).map(ULong::longValue).collect(Collectors.toList());
        return permissionManager.getPermissionRoleByIds(roleIds);
    }

    @Override
    protected List<DescribeMenuItem> getMenuItemsFromRoleDbOp(Long userId, LongIdentifier group) {
        List<DescribePermissionRole> roles = getRolesDbOp(userId, group);
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        List<Long> roleIds = roles.stream().map(DescribePermissionRole::getId).collect(Collectors.toList());
        return menuItemManager.getMenuItemsFromRole(roleIds);
    }

    private UserBelongsToGroupCoreRecord userBelongsToGroupCoreRecordForInsert(CreateUserBelongsToGroupCoreRequest request, Long opUserId) {
        LongIdentifier group = request.getGroup();
        LocalDateTime now = LocalDateTime.now();
        return new UserBelongsToGroupCoreRecord(ULong.valueOf(request.getId()),
                ULong.valueOf(request.getUserBelongsToGroupId()),
                ULong.valueOf(request.getUserId()),
                UShort.valueOf(group.getType().getCode()),
                ULong.valueOf(group.getValue()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserBelongsToGroupGeneralRecord userBelongsToGroupGeneralRecordForInsert(CreateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserBelongsToGroupGeneralRecord(ULong.valueOf(request.getId()),
                ULong.valueOf(request.getUserBelongsToGroupId()),
                request.getNickname(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserBelongsToGroupRoleRecord userBelongsToGroupRoleRecordForInsert(CreateUserBelongsToGroupRoleRequest request, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserBelongsToGroupRoleRecord(ULong.valueOf(request.getId()),
                ULong.valueOf(request.getUserBelongsToGroupId()),
                ULong.valueOf(request.getPermissionRoleId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserBelongsToGroupGeneralRecord userBelongsToGroupGeneralRecordForUpdate(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        UserBelongsToGroupGeneralRecord record = new UserBelongsToGroupGeneralRecord();
        record.setUserBelongsToGroupGeneralId(ULong.valueOf(request.getId()));
        record.setNickname(request.getNickname());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        return record;
    }

    private SelectConditionStep<Record1<ULong>> userBelongsToGroupCoreCondition(Long userId, LongIdentifier group) {
        Configuration configuration = JooqContext.getConfiguration();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_BELONGS_TO_GROUP_CORE.USER_BELONGS_TO_GROUP_CORE_ID)
                .from(USER_BELONGS_TO_GROUP_CORE)
                .where(USER_BELONGS_TO_GROUP_CORE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_CORE.GROUP_IDENTIFIER.eq(ULong.valueOf((group.getValue()))))
                .and(USER_BELONGS_TO_GROUP_CORE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return existsRecordSelector;
    }

    private SelectConditionStep<Record1<ULong>> userBelongsToGroupGeneralCondition(Long userBelongsToGroupId) {
        Configuration configuration = JooqContext.getConfiguration();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_BELONGS_TO_GROUP_GENERAL.USER_BELONGS_TO_GROUP_GENERAL_ID)
                .from(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_BELONGS_TO_GROUP_ID.eq(ULong.valueOf(userBelongsToGroupId)))
                .and(USER_BELONGS_TO_GROUP_GENERAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return existsRecordSelector;
    }

    private SelectConditionStep<Record1<ULong>> userBelongsToGroupRoleCondition(Long userBelongsToGroupId, Long permissionRoleId) {
        Configuration configuration = JooqContext.getConfiguration();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ROLE_ID)
                .from(USER_BELONGS_TO_GROUP_ROLE)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_BELONGS_TO_GROUP_ID.eq(ULong.valueOf(userBelongsToGroupId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.PERMISSION_ROLE_ID.eq(ULong.valueOf(permissionRoleId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return existsRecordSelector;
    }

    private DescribeUserBelongsToGroupCore copyFrom(UserBelongsToGroupCoreRecord record) {
        DescribeUserBelongsToGroupCore describe = new DescribeUserBelongsToGroupCore();
        describe.setId(record.getUserBelongsToGroupCoreId().longValue());
        describe.setUserBelongsToGroupId(record.getUserBelongsToGroupId().longValue());
        describe.setUserId(record.getUserId().longValue());
        IdentifierType groupType = IdentifierTypeRegistry.defaultRegistry.lookup(record.getGroupType().intValue());
        describe.setGroup(new LongIdentifier(record.getGroupIdentifier().longValue(), groupType));
        return describe;
    }

    private DescribeUserBelongsToGroupGeneral copyFrom(UserBelongsToGroupGeneralRecord record) {
        DescribeUserBelongsToGroupGeneral describe = new DescribeUserBelongsToGroupGeneral();
        describe.setId(record.getUserBelongsToGroupGeneralId().longValue());
        describe.setUserBelongsToGroupId(record.getUserBelongsToGroupId().longValue());
        describe.setNickname(record.getNickname());
        return describe;
    }

    private DescribeUserBelongsToGroupRole copyFrom(UserBelongsToGroupRoleRecord record) {
        DescribeUserBelongsToGroupRole describe = new DescribeUserBelongsToGroupRole();
        describe.setId(record.getUserBelongsToGroupRoleId().longValue());
        describe.setUserBelongsToGroupId(record.getUserBelongsToGroupId().longValue());
        describe.setPermissionRoleId(record.getPermissionRoleId().longValue());
        return describe;
    }

}
