package com.wuda.foundation.core.user.impl;

import com.wuda.foundation.core.commons.DescribeMenuItemCore;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.*;
import com.wuda.foundation.core.user.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupGeneralRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupRoleRecord;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.FoundationConfiguration;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuda.foundation.jooq.code.generation.user.Tables.USER_BELONGS_TO_GROUP_GENERAL;
import static com.wuda.foundation.jooq.code.generation.user.Tables.USER_BELONGS_TO_GROUP_ROLE;

public class UserBelongsToGroupManagerImpl extends AbstractUserBelongsToGroupManager implements JooqCommonDbOp {

    private PermissionGrantManager permissionGrantManager;

    private PermissionManager permissionManager;

    private MenuManager menuManager;

    public UserBelongsToGroupManagerImpl(PermissionGrantManager permissionGrantManager,
                                         PermissionManager permissionManager,
                                         MenuManager menuManager) {
        this.permissionGrantManager = permissionGrantManager;
        this.permissionManager = permissionManager;
        this.menuManager = menuManager;
    }


    @Override
    protected void removeUserFromGroupDbOp(RemoveUserFromGroupRequest request, Long opUserId) {
        deleteUserBelongsToGroupGeneral(request.getUserId(), request.getGroup());
        deleteUserBelongsToGroupRole(request.getUserId(), request.getGroup());
    }

    @Override
    protected CreateResult createUserBelongsToGroupGeneralDbOp(CreateUserBelongsToGroupGeneralRequest request, CreateMode createMode, Long opUserId) {
        UserBelongsToGroupGeneralRecord record = userBelongsToGroupGeneralRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = userBelongsToGroupGeneralCondition(request.getUserId(), request.getGroup());
        return insertDispatcher(JooqContext.getDataSource(), createMode, USER_BELONGS_TO_GROUP_GENERAL, record, existsRecordSelector);
    }

    @Override
    protected void updateUserBelongsToGroupGeneralDbOp(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        DescribeUserBelongsToGroupGeneral describeUserBelongsToGroupGeneral = getUserBelongsToGroupGeneral(request.getUserId(), request.getGroup());
        if (describeUserBelongsToGroupGeneral == null) {
            return;
        }
        UserBelongsToGroupGeneralRecord record = userBelongsToGroupGeneralRecordForUpdate(describeUserBelongsToGroupGeneral.getId(), request, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneralDbOp(Long userId, LongIdentifier group) {
        UserBelongsToGroupGeneralRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .fetchOne();
        return copyFrom(record);
    }

    @Override
    protected CreateResult createUserBelongsToGroupRoleDbOp(CreateUserBelongsToGroupRoleRequest request, CreateMode createMode, Long opUserId) {
        UserBelongsToGroupRoleRecord record = userBelongsToGroupRoleRecordForInsert(request, opUserId);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = userBelongsToGroupRoleCondition(request.getUserId(), request.getGroup(), request.getPermissionRoleId());
        return insertDispatcher(JooqContext.getDataSource(), createMode, USER_BELONGS_TO_GROUP_ROLE, record, existsRecordSelector);
    }

    @Override
    protected void removeUsersRoleFromGroupDbOp(RemoveUsersRoleFromGroupRequest request, Long opUserId) {
        deleteUserBelongsToGroupRole(request.getUserId(), request.getGroup());
    }

    @Override
    protected DescribeUserBelongsToGroupRole getUserBelongsToGroupRoleDbOp(Long userId, LongIdentifier group) {
        UserBelongsToGroupRoleRecord record = JooqContext.getOrCreateDSLContext().selectFrom(USER_BELONGS_TO_GROUP_ROLE)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .fetchOne();
        return copyFrom(record);
    }

    @Override
    protected List<LongIdentifier> getGroupsDbOp(Long userId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record2<UShort, ULong>> results = dslContext.select(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE, USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER)
                .from(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_GENERAL.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (results == null || results.isEmpty()) {
            return null;
        }
        List<LongIdentifier> groups = new ArrayList<>(results.size());
        results.forEach((result -> {
            long groupId = result.get(USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER).longValue();
            int _groupType = result.get(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE).intValue();
            IdentifierType groupType = FoundationConfiguration.getGlobalSingletonInstance().getIdentifierTypeRegistry().lookup(_groupType);
            LongIdentifier group = new LongIdentifier(groupId, groupType);
            groups.add(group);
        }));
        return groups;
    }

    @Override
    protected List<Long> getMembersDbOp(LongIdentifier group) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record1<ULong>> results = dslContext.select(USER_BELONGS_TO_GROUP_GENERAL.USER_ID)
                .from(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .and(USER_BELONGS_TO_GROUP_GENERAL.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (results == null || results.isEmpty()) {
            return null;
        }
        List<Long> userIds = new ArrayList<>(results.size());
        results.forEach((result -> {
            long userId = result.get(USER_BELONGS_TO_GROUP_GENERAL.USER_ID).longValue();
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
                .from(USER_BELONGS_TO_GROUP_ROLE)
                .where(USER_BELONGS_TO_GROUP_ROLE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .and(USER_BELONGS_TO_GROUP_ROLE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED.eq(notDeleted()))
                .and(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (result == null || result.isEmpty()) {
            return null;
        }
        List<Long> roleIds = result.stream().map(Record1::value1).map(ULong::longValue).collect(Collectors.toList());
        return permissionManager.getPermissionRoleByIds(roleIds);
    }

    @Override
    protected List<DescribeMenuItemCore> getMenuItemsFromRoleDbOp(Long userId, LongIdentifier group) {
        List<DescribePermissionRole> roles = getRolesDbOp(userId, group);
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        List<Long> roleIds = roles.stream().map(DescribePermissionRole::getId).collect(Collectors.toList());
        return menuManager.getMenuItemsFromRole(roleIds);
    }

    private UserBelongsToGroupGeneralRecord userBelongsToGroupGeneralRecordForInsert(CreateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserBelongsToGroupGeneralRecord(ULong.valueOf(request.getId()),
                ULong.valueOf(request.getUserId()),
                UShort.valueOf(request.getGroup().getType().getCode()),
                ULong.valueOf(request.getGroup().getValue()),
                request.getNickname(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserBelongsToGroupRoleRecord userBelongsToGroupRoleRecordForInsert(CreateUserBelongsToGroupRoleRequest request, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserBelongsToGroupRoleRecord(ULong.valueOf(request.getId()),
                ULong.valueOf(request.getUserId()),
                UShort.valueOf(request.getGroup().getType().getCode()),
                ULong.valueOf(request.getGroup().getValue()),
                ULong.valueOf(request.getPermissionRoleId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserBelongsToGroupGeneralRecord userBelongsToGroupGeneralRecordForUpdate(Long id, UpdateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        UserBelongsToGroupGeneralRecord record = new UserBelongsToGroupGeneralRecord();
        record.setId(ULong.valueOf(id));
        record.setNickname(request.getNickname());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        return record;
    }

    private SelectConditionStep<Record1<ULong>> userBelongsToGroupGeneralCondition(Long userId, LongIdentifier group) {
        Configuration configuration = JooqContext.getConfiguration();
        return DSL.using(configuration)
                .select(USER_BELONGS_TO_GROUP_GENERAL.ID)
                .from(USER_BELONGS_TO_GROUP_GENERAL)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER.eq(ULong.valueOf((group.getValue()))))
                .and(USER_BELONGS_TO_GROUP_GENERAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private SelectConditionStep<Record1<ULong>> userBelongsToGroupRoleCondition(Long userId, LongIdentifier group, Long permissionRoleId) {
        Configuration configuration = JooqContext.getConfiguration();
        return DSL.using(configuration)
                .select(USER_BELONGS_TO_GROUP_ROLE.ID)
                .from(USER_BELONGS_TO_GROUP_ROLE)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_IDENTIFIER.eq(ULong.valueOf((group.getValue()))))
                .and(USER_BELONGS_TO_GROUP_ROLE.PERMISSION_ROLE_ID.eq(ULong.valueOf(permissionRoleId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
    }

    private DescribeUserBelongsToGroupGeneral copyFrom(UserBelongsToGroupGeneralRecord record) {
        if (record == null) {
            return null;
        }
        DescribeUserBelongsToGroupGeneral describe = new DescribeUserBelongsToGroupGeneral();
        describe.setId(record.getId().longValue());
        describe.setUserId(record.getUserId().longValue());
        IdentifierType groupType = FoundationConfiguration.getGlobalSingletonInstance().getIdentifierTypeRegistry().lookup(record.getGroupType().intValue());
        LongIdentifier group = new LongIdentifier(record.getGroupIdentifier().longValue(), groupType);
        describe.setGroup(group);
        describe.setNickname(record.getNickname());
        return describe;
    }

    private DescribeUserBelongsToGroupRole copyFrom(UserBelongsToGroupRoleRecord record) {
        DescribeUserBelongsToGroupRole describe = new DescribeUserBelongsToGroupRole();
        describe.setId(record.getId().longValue());
        IdentifierType groupType = FoundationConfiguration.getGlobalSingletonInstance().getIdentifierTypeRegistry().lookup(record.getGroupType().intValue());
        LongIdentifier group = new LongIdentifier(record.getGroupIdentifier().longValue(), groupType);
        describe.setGroup(group);
        describe.setPermissionRoleId(record.getPermissionRoleId().longValue());
        return describe;
    }

    private void deleteUserBelongsToGroupGeneral(Long userId, LongIdentifier group) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(USER_BELONGS_TO_GROUP_GENERAL)
                .set(USER_BELONGS_TO_GROUP_GENERAL.IS_DELETED, USER_BELONGS_TO_GROUP_GENERAL.ID)
                .where(USER_BELONGS_TO_GROUP_GENERAL.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_GENERAL.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .execute();
    }

    private void deleteUserBelongsToGroupRole(Long userId, LongIdentifier group) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(USER_BELONGS_TO_GROUP_ROLE)
                .set(USER_BELONGS_TO_GROUP_ROLE.IS_DELETED, USER_BELONGS_TO_GROUP_ROLE.ID)
                .where(USER_BELONGS_TO_GROUP_ROLE.USER_ID.eq(ULong.valueOf(userId)))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_TYPE.eq(UShort.valueOf(group.getType().getCode())))
                .and(USER_BELONGS_TO_GROUP_ROLE.GROUP_IDENTIFIER.eq(ULong.valueOf(group.getValue())))
                .execute();
    }

}
