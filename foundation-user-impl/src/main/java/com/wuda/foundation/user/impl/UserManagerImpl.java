package com.wuda.foundation.user.impl;

import com.wuda.foundation.commons.*;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.*;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserAccountRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserEmailRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserPhoneRecord;
import com.wuda.foundation.user.impl.jooq.generation.tables.records.UserRecord;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class UserManagerImpl extends AbstractUserManager {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public boolean existsDbOp(Identifier<String> identifier) {
        return false;
    }

    @Override
    public long createUserDbOp(CreateUser createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        long userId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        UserRecord userRecord = new UserRecord(ULong.valueOf(userId),
                UByte.valueOf(createUser.getUserType().getCode()),
                UByte.valueOf(createUser.getUserState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        userRecord.attach(configuration);
        userRecord.insert();

        CreateUserAccount createUserAccount = createUser.getUserAccount();
        for (Identifier<String> identifier : createUserAccount.getPrincipals()) {
            if (identifier.getType() == BuiltinIdentifierType.USERNAME) {
                long userAccountId = keyGenerator.next();
                UserAccountRecord userAccountRecord = new UserAccountRecord(ULong.valueOf(userAccountId),
                        ULong.valueOf(userId),
                        identifier.getValue(), createUserAccount.getPassword(),
                        UByte.valueOf(createUserAccount.getState().getCode()),
                        now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
                userAccountRecord.attach(configuration);
                userAccountRecord.insert();
            } else if (identifier.getType() == BuiltinIdentifierType.EMAIL) {
                EmailIdentifier emailIdentifier = (EmailIdentifier) identifier;
                long emailId = emailManager.addEmail(emailIdentifier.getValue(), emailIdentifier.getEmailState(), opUserId);
                long id = keyGenerator.next();
                UserEmailRecord userEmailRecord = new UserEmailRecord(ULong.valueOf(id),
                        ULong.valueOf(userId),
                        ULong.valueOf(emailId),
                        UByte.valueOf(BuiltinEmailUse.ZERO.getCode()),
                        UByte.valueOf(BuiltinUserEmailState.ZERO.getCode()),
                        "",
                        now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
                userEmailRecord.attach(configuration);
                userEmailRecord.insert();

            } else if (identifier.getType() == BuiltinIdentifierType.MOBILE_PHONE) {
                MobilePhoneIdentifier mobilePhoneIdentifier = (MobilePhoneIdentifier) identifier;
                long phoneId = phoneManager.addPhone(identifier.getValue(), mobilePhoneIdentifier.getPhoneState(), BuiltinPhoneType.ZERO, opUserId);
                long id = keyGenerator.next();
                UserPhoneRecord userPhoneRecord = new UserPhoneRecord(ULong.valueOf(id),
                        ULong.valueOf(userId),
                        ULong.valueOf(phoneId),
                        UByte.valueOf(BuiltinPhoneUse.ZERO.getCode()),
                        UByte.valueOf(BuiltinUserPhoneState.ZERO.getCode()),
                        "",
                        now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
                userPhoneRecord.attach(configuration);
                userPhoneRecord.insert();
            }
        }
        return userId;
    }
}
