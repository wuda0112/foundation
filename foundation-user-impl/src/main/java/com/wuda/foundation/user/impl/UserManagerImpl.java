package com.wuda.foundation.user.impl;

import com.wuda.foundation.commons.*;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.*;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
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
import java.util.List;

public class UserManagerImpl extends AbstractUserManager {

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setPhoneManager(PhoneManager phoneManager) {
        this.phoneManager = phoneManager;
    }

    public void setKeyGenerator(KeyGeneratorSnowflake keyGeneratorSnowflake) {
        this.keyGeneratorSnowflake = keyGeneratorSnowflake;
    }

    private DataSource dataSource;
    private EmailManager emailManager;
    private PhoneManager phoneManager;
    private KeyGeneratorSnowflake keyGeneratorSnowflake;


    @Override
    public boolean existsDbOp(Identifier<String> identifier) {
        return false;
    }

    @Override
    public long addUserDbOp(UserType userType, UserState userState, List<Identifier<String>> identifiers, String password, UserAccountState userAccountState, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        long userId = keyGeneratorSnowflake.next();
        LocalDateTime now = LocalDateTime.now();
        UserRecord userRecord = new UserRecord(ULong.valueOf(userId),
                UByte.valueOf(userType.getCode()),
                UByte.valueOf(userState.getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        userRecord.attach(configuration);
        userRecord.insert();

        for (Identifier<String> identifier : identifiers) {
            if (identifier.getType() == BuiltinIdentifierType.USERNAME) {
                long userAccountId = keyGeneratorSnowflake.next();
                UserAccountRecord userAccountRecord = new UserAccountRecord(ULong.valueOf(userAccountId),
                        ULong.valueOf(userId),
                        identifier.getValue(), password,
                        UByte.valueOf(userAccountState.getCode()),
                        now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
                userAccountRecord.attach(configuration);
                userAccountRecord.insert();
            } else if (identifier.getType() == BuiltinIdentifierType.EMAIL) {
                EmailIdentifier emailIdentifier = (EmailIdentifier) identifier;
                long emailId = emailManager.addEmail(emailIdentifier.getValue(), emailIdentifier.getEmailState(), opUserId);
                long id = keyGeneratorSnowflake.next();
                UserEmailRecord userEmailRecord = new UserEmailRecord(ULong.valueOf(id),
                        ULong.valueOf(userId),
                        ULong.valueOf(emailId),
                        UByte.valueOf(BuiltinEmailUsage.ZERO.getCode()),
                        UByte.valueOf(BuiltinUserEmailState.ZERO.getCode()),
                        "",
                        now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
                userEmailRecord.attach(configuration);
                userEmailRecord.insert();

            } else if (identifier.getType() == BuiltinIdentifierType.MOBILE_PHONE) {
                MobilePhoneIdentifier mobilePhoneIdentifier = (MobilePhoneIdentifier) identifier;
                long phoneId = phoneManager.addPhone(identifier.getValue(), mobilePhoneIdentifier.getPhoneState(), BuiltinPhoneType.ZERO, opUserId);
                long id = keyGeneratorSnowflake.next();
                UserPhoneRecord userPhoneRecord = new UserPhoneRecord(ULong.valueOf(id),
                        ULong.valueOf(userId),
                        ULong.valueOf(phoneId),
                        UByte.valueOf(BuiltinPhoneUsage.ZERO.getCode()),
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
