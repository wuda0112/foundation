package com.wuda.foundation.user.impl;

import com.wuda.foundation.commons.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserAccountRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserEmailRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserPhoneRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static com.wuda.foundation.user.impl.jooq.generation.tables.User.USER;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserAccount.USER_ACCOUNT;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserEmail.USER_EMAIL;
import static com.wuda.foundation.user.impl.jooq.generation.tables.UserPhone.USER_PHONE;
import static org.jooq.impl.DSL.param;

public class UserManagerImpl extends AbstractUserManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void createUserDbOp(CreateUser createUser, Long opUserId) {
        Field[] fields = generateFields(createUser, opUserId);
        insert(dataSource, USER, fields, USER.USER_ID);
    }

    @Override
    protected void createUserDbOp(List<CreateUser> userList, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<UserRecord> insertSetStep = dslContext.insertInto(USER);
        int last = userList.size() - 1;
        for (int i = 0; i < last; i++) {
            CreateUser createUser = userList.get(i);
            insertSetStep.values(generateFields(createUser, opUserId));
        }
        insertSetStep.values(userList.get(last), opUserId).execute();
    }

    @Override
    protected void createUserAccountDbOp(CreateUserAccount createUserAccount, Long opUserId) {
        Field[] fields = generateFields(createUserAccount, opUserId);
        insert(dataSource, USER_ACCOUNT, fields, USER_ACCOUNT.USER_ACCOUNT_ID);
    }

    @Override
    protected void createUserAccountDbOp(List<CreateUserAccount> userAccounts, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<UserAccountRecord> insertSetStep = dslContext.insertInto(USER_ACCOUNT);
        int last = userAccounts.size() - 1;
        for (int i = 0; i < last; i++) {
            CreateUserAccount createUserAccount = userAccounts.get(i);
            insertSetStep.values(generateFields(createUserAccount, opUserId));
        }
        insertSetStep.values(userAccounts.get(last), opUserId).execute();
    }

    @Override
    protected Long bindUserEmailDbOp(BindUserEmail bindUserEmail, InsertMode insertMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<UserEmailRecord> existsRecordSelector = DSL.using(configuration)
                .selectFrom(USER_EMAIL)
                .where(USER_EMAIL.USER_ID.eq(ULong.valueOf(bindUserEmail.getUserId())))
                .and(USER_EMAIL.EMAIL_ID.eq(ULong.valueOf(bindUserEmail.getEmailId())))
                .and(USER_EMAIL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = generateFields(bindUserEmail, opUserId);
        return insertDispatcher(dataSource, insertMode, USER_EMAIL, fields, existsRecordSelector, USER_EMAIL.ID);
    }

    @Override
    protected void bindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<UserEmailRecord> insertSetStep = dslContext.insertInto(USER_EMAIL);
        int last = bindUserEmails.size() - 1;
        for (int i = 0; i < last; i++) {
            BindUserEmail bindUserEmail = bindUserEmails.get(i);
            insertSetStep.values(generateFields(bindUserEmail, opUserId));
        }
        insertSetStep.values(bindUserEmails.get(last), opUserId).execute();
    }

    @Override
    protected Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, InsertMode insertMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<UserPhoneRecord> existsRecordSelector = DSL.using(configuration)
                .selectFrom(USER_PHONE)
                .where(USER_PHONE.USER_ID.eq(ULong.valueOf(bindUserPhone.getUserId())))
                .and(USER_PHONE.PHONE_ID.eq(ULong.valueOf(bindUserPhone.getPhoneId())))
                .and(USER_PHONE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = generateFields(bindUserPhone, opUserId);
        return insertDispatcher(dataSource, insertMode, USER_PHONE, fields, existsRecordSelector, USER_PHONE.ID);
    }

    @Override
    protected void bindUserPhoneDbOp(List<BindUserPhone> bindUserPhones, InsertMode insertMode, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<UserPhoneRecord> insertSetStep = dslContext.insertInto(USER_PHONE);
        int last = bindUserPhones.size() - 1;
        for (int i = 0; i < last; i++) {
            BindUserPhone bindUserPhone = bindUserPhones.get(i);
            insertSetStep.values(generateFields(bindUserPhone, opUserId));
        }
        insertSetStep.values(bindUserPhones.get(last), opUserId).execute();
    }

    @Override
    public boolean existsDbOp(Identifier<String> identifier) {
        return false;
    }

    @Override
    public long createUserDbOp(CreateUserWithAccount createUserWithAccount, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId) {
        CreateUser createUser = createUserWithAccount.getUser();
        createUserDbOp(createUserWithAccount.getUser(), opUserId);
        createUserAccountDbOp(createUserWithAccount.getUserAccount(), opUserId);

        long userId = createUser.getId();

        CreateEmail createEmail = createUserWithAccount.getEmail();
        if (createEmail != null) {
            long emailId = emailManager.createEmail(createEmail, InsertMode.DIRECT, opUserId);
            long id = keyGenerator.next();
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
            long phoneId = phoneManager.createPhone(createPhone, InsertMode.DIRECT, opUserId);
            long id = keyGenerator.next();
            BindUserPhone binding = new BindUserPhone.Builder()
                    .setId(id)
                    .setUserId(userId)
                    .setPhoneId(phoneId)
                    .setUse(BuiltinPhoneUse.FOR_SIGN_IN)
                    .setState(BuiltinUserPhoneState.ZERO)
                    .build();
            bindUserPhoneDbOp(binding, InsertMode.DIRECT, opUserId);
        }
        return userId;
    }

    private Field[] generateFields(CreateUser createUser, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(USER.USER_ID.getName(), ULong.valueOf(createUser.getId())),
                param(USER.TYPE.getName(), UByte.valueOf(createUser.getUserType().getCode())),
                param(USER.STATE.getName(), UByte.valueOf(createUser.getUserState().getCode())),
                param(USER.CREATE_TIME.getName(), now),
                param(USER.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER.LAST_MODIFY_TIME.getName(), now),
                param(USER.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }

    private Field[] generateFields(CreateUserAccount createUserAccount, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(USER_ACCOUNT.USER_ACCOUNT_ID.getName(), ULong.valueOf(createUserAccount.getId())),
                param(USER_ACCOUNT.USER_ID.getName(), ULong.valueOf(createUserAccount.getUserId())),
                param(USER_ACCOUNT.USERNAME.getName(), createUserAccount.getUsername()),
                param(USER_ACCOUNT.PASSWORD.getName(), createUserAccount.getPassword()),
                param(USER_ACCOUNT.STATE.getName(), UByte.valueOf(createUserAccount.getState().getCode())),
                param(USER_ACCOUNT.CREATE_TIME.getName(), now),
                param(USER_ACCOUNT.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_ACCOUNT.LAST_MODIFY_TIME.getName(), now),
                param(USER_ACCOUNT.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_ACCOUNT.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }

    private Field[] generateFields(BindUserEmail bindUserEmail, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(USER_EMAIL.ID.getName(), ULong.valueOf(bindUserEmail.getId())),
                param(USER_EMAIL.USER_ID.getName(), ULong.valueOf(bindUserEmail.getUserId())),
                param(USER_EMAIL.EMAIL_ID.getName(), ULong.valueOf(bindUserEmail.getEmailId())),
                param(USER_EMAIL.USE.getName(), UByte.valueOf(bindUserEmail.getUse().getCode())),
                param(USER_EMAIL.STATE.getName(), UByte.valueOf(bindUserEmail.getState().getCode())),
                param(USER_EMAIL.DESCRIPTION.getName(), "desc"),
                param(USER_EMAIL.CREATE_TIME.getName(), now),
                param(USER_EMAIL.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_EMAIL.LAST_MODIFY_TIME.getName(), now),
                param(USER_EMAIL.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_EMAIL.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }

    private Field[] generateFields(BindUserPhone bindUserPhone, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(USER_PHONE.ID.getName(), ULong.valueOf(bindUserPhone.getId())),
                param(USER_PHONE.USER_ID.getName(), ULong.valueOf(bindUserPhone.getUserId())),
                param(USER_PHONE.PHONE_ID.getName(), ULong.valueOf(bindUserPhone.getPhoneId())),
                param(USER_PHONE.USE.getName(), UByte.valueOf(bindUserPhone.getUse().getCode())),
                param(USER_PHONE.STATE.getName(), UByte.valueOf(bindUserPhone.getState().getCode())),
                param(USER_PHONE.DESCRIPTION.getName(), "desc"),
                param(USER_PHONE.CREATE_TIME.getName(), now),
                param(USER_PHONE.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_PHONE.LAST_MODIFY_TIME.getName(), now),
                param(USER_PHONE.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(USER_PHONE.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }
}
