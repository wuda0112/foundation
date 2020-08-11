package com.wuda.foundation.user.impl;

import com.wuda.foundation.commons.BuiltinEmailUse;
import com.wuda.foundation.commons.BuiltinPhoneUse;
import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.SingleInsertResult;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.user.AbstractUserManager;
import com.wuda.foundation.user.BindUserEmail;
import com.wuda.foundation.user.BindUserPhone;
import com.wuda.foundation.user.BuiltinUserEmailState;
import com.wuda.foundation.user.BuiltinUserPhoneState;
import com.wuda.foundation.user.CreateUser;
import com.wuda.foundation.user.CreateUserAccount;
import com.wuda.foundation.user.CreateUserWithAccount;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserAccountRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserEmailRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserPhoneRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserRecord;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.user.impl.jooq.generation.tables.User.USER;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserAccount.USER_ACCOUNT;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserEmail.USER_EMAIL;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserPhone.USER_PHONE;

public class UserManagerImpl extends AbstractUserManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void createUserDbOp(CreateUser createUser, Long opUserId) {
        insert(dataSource, USER, userRecordForInsert(createUser, opUserId));
    }

    @Override
    protected void directBatchInsertUserDbOp(List<CreateUser> userList, Long opUserId) {
        batchInsert(dataSource, USER, userRecordsForInsert(userList, opUserId));
    }

    @Override
    protected void createUserAccountDbOp(CreateUserAccount createUserAccount, Long opUserId) throws AlreadyExistsException {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_ACCOUNT.USER_ACCOUNT_ID)
                .from(USER_ACCOUNT)
                .where(USER_ACCOUNT.USERNAME.eq(createUserAccount.getUsername()))
                .and(USER_ACCOUNT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        SingleInsertResult result = insertAfterSelectCheck(dataSource, USER_ACCOUNT, userAccountRecordForInsert(createUserAccount, opUserId), existsRecordSelector);
        if (result.getExistsRecordId() != null) {
            throw new AlreadyExistsException("username = " + createUserAccount.getUsername() + ",已经存在");
        }
    }

    @Override
    protected void directBatchInsertUserAccountDbOp(List<CreateUserAccount> userAccounts, Long opUserId) {
        batchInsert(dataSource, USER_ACCOUNT, userAccountRecordsForInsert(userAccounts, opUserId));
    }

    @Override
    protected Long bindUserEmailDbOp(BindUserEmail bindUserEmail, InsertMode insertMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_EMAIL.ID)
                .from(USER_EMAIL)
                .where(USER_EMAIL.USER_ID.eq(ULong.valueOf(bindUserEmail.getUserId())))
                .and(USER_EMAIL.EMAIL_ID.eq(ULong.valueOf(bindUserEmail.getEmailId())))
                .and(USER_EMAIL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, insertMode, USER_EMAIL, userEmailRecordForInsert(bindUserEmail, opUserId), existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchBindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId) {
        batchInsert(dataSource, USER_EMAIL, userEmailRecordsForInsert(bindUserEmails, opUserId));
    }

    @Override
    protected Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, InsertMode insertMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_PHONE.ID)
                .from(USER_PHONE)
                .where(USER_PHONE.USER_ID.eq(ULong.valueOf(bindUserPhone.getUserId())))
                .and(USER_PHONE.PHONE_ID.eq(ULong.valueOf(bindUserPhone.getPhoneId())))
                .and(USER_PHONE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, insertMode, USER_PHONE, userPhoneRecordForInsert(bindUserPhone, opUserId), existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchBindUserPhoneDbOp(List<BindUserPhone> bindUserPhones, Long opUserId) {
        batchInsert(dataSource, USER_PHONE, userPhoneRecordsForInsert(bindUserPhones, opUserId));
    }

    @Override
    public boolean existsDbOp(Identifier<String> identifier) {
        return false;
    }

    @Override
    public long createUserDbOp(CreateUserWithAccount createUserWithAccount, EmailManager emailManager, PhoneManager phoneManager, Long opUserId) throws AlreadyExistsException {
        createUserAccountDbOp(createUserWithAccount.getUserAccount(), opUserId);

        CreateUser createUser = createUserWithAccount.getUser();
        long userId = createUser.getId();

        CreateEmail createEmail = createUserWithAccount.getEmail();
        if (createEmail != null) {
            long emailId = emailManager.createEmail(createEmail, opUserId);
            long id = FoundationContext.getLongKeyGenerator().next();
            BindUserEmail binding = new BindUserEmail.Builder()
                    .setId(id)
                    .setUserId(userId)
                    .setEmailId(emailId)
                    .setUse(BuiltinEmailUse.FOR_SIGN_IN)
                    .setState(BuiltinUserEmailState.ZERO)
                    .build();
            bindUserEmailDbOp(binding, InsertMode.DIRECT, opUserId);
        }
        CreatePhone createPhone = createUserWithAccount.getPhone();
        if (createPhone != null) {
            long phoneId = phoneManager.createPhone(createPhone, opUserId);
            long id = FoundationContext.getLongKeyGenerator().next();
            BindUserPhone binding = new BindUserPhone.Builder()
                    .setId(id)
                    .setUserId(userId)
                    .setPhoneId(phoneId)
                    .setUse(BuiltinPhoneUse.FOR_SIGN_IN)
                    .setState(BuiltinUserPhoneState.ZERO)
                    .build();
            bindUserPhoneDbOp(binding, InsertMode.DIRECT, opUserId);
        }
        createUserDbOp(createUserWithAccount.getUser(), opUserId);
        return userId;
    }

    private List<UserRecord> userRecordsForInsert(List<CreateUser> createUsers, Long opUserId) {
        List<UserRecord> list = new ArrayList<>(createUsers.size());
        for (CreateUser createUser : createUsers) {
            list.add(userRecordForInsert(createUser, opUserId));
        }
        return list;
    }

    private UserRecord userRecordForInsert(CreateUser createUser, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserRecord(ULong.valueOf(createUser.getId()),
                UByte.valueOf(createUser.getUserType().getCode()),
                UByte.valueOf(createUser.getUserState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserAccountRecord userAccountRecordForInsert(CreateUserAccount createUserAccount, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserAccountRecord(ULong.valueOf(createUserAccount.getId()),
                ULong.valueOf(createUserAccount.getUserId()),
                createUserAccount.getUsername(),
                createUserAccount.getPassword(),
                UByte.valueOf(createUserAccount.getState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserAccountRecord> userAccountRecordsForInsert(List<CreateUserAccount> createUserAccounts, Long opUserId) {
        List<UserAccountRecord> list = new ArrayList<>(createUserAccounts.size());
        for (CreateUserAccount createUserAccount : createUserAccounts) {
            list.add(userAccountRecordForInsert(createUserAccount, opUserId));
        }
        return list;
    }

    private UserEmailRecord userEmailRecordForInsert(BindUserEmail bindUserEmail, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserEmailRecord(ULong.valueOf(bindUserEmail.getId()),
                ULong.valueOf(bindUserEmail.getUserId()),
                ULong.valueOf(bindUserEmail.getEmailId()),
                UByte.valueOf(bindUserEmail.getUse().getCode()),
                UByte.valueOf(bindUserEmail.getState().getCode()),
                bindUserEmail.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserEmailRecord> userEmailRecordsForInsert(List<BindUserEmail> bindUserEmails, Long opUserId) {
        List<UserEmailRecord> list = new ArrayList<>(bindUserEmails.size());
        for (BindUserEmail bindUserEmail : bindUserEmails) {
            list.add(userEmailRecordForInsert(bindUserEmail, opUserId));
        }
        return list;
    }

    private UserPhoneRecord userPhoneRecordForInsert(BindUserPhone bindUserPhone, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserPhoneRecord(ULong.valueOf(bindUserPhone.getId()),
                ULong.valueOf(bindUserPhone.getUserId()),
                ULong.valueOf(bindUserPhone.getPhoneId()),
                UByte.valueOf(bindUserPhone.getUse().getCode()),
                UByte.valueOf(bindUserPhone.getState().getCode()),
                bindUserPhone.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserPhoneRecord> userPhoneRecordsForInsert(List<BindUserPhone> bindUserPhones, Long opUserId) {
        List<UserPhoneRecord> list = new ArrayList<>(bindUserPhones.size());
        for (BindUserPhone bindUserPhone : bindUserPhones) {
            list.add(userPhoneRecordForInsert(bindUserPhone, opUserId));
        }
        return list;
    }
}
