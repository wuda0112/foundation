package com.wuda.foundation.user.impl.jooq;

import com.wuda.foundation.commons.BuiltinEmailUsage;
import com.wuda.foundation.commons.BuiltinPhoneUsage;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.jooq.gen.tables.records.UserAccountRecord;
import com.wuda.foundation.user.impl.jooq.gen.tables.records.UserEmailRecord;
import com.wuda.foundation.user.impl.jooq.gen.tables.records.UserPhoneRecord;
import com.wuda.foundation.user.impl.jooq.gen.tables.records.UserRecord;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class UserManagerImpl implements UserManager {

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setPhoneManager(PhoneManager phoneManager) {
        this.phoneManager = phoneManager;
    }

    private DataSource dataSource;
    private EmailManager emailManager;
    private PhoneManager phoneManager;


    @Override
    public boolean exists(Identifier<String> identifier) {
        return false;
    }

    @Override
    public long addUser(UserType type, UserState userState, List<Identifier<String>> identifiers, String password, UserAccountState userAccountState) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        long userId = 0;
        LocalDateTime now = LocalDateTime.now();
        UserRecord userRecord = new UserRecord();
        userRecord.setCreateUserId(ULong.valueOf(userId));
        userRecord.setType(UByte.valueOf(type.get()));
        userRecord.setUserId(userId);
        userRecord.setCreateTime(now);
        userRecord.setLastModifyUserId(ULong.valueOf(userId));
        userRecord.setLastModifyTime(now);
        userRecord.setState(UByte.valueOf(userState.get()));
        userRecord.attach(configuration);
        userRecord.insert();

        for (Identifier<String> identifier : identifiers) {
            if (identifier.getType() == BuiltinUserIdentifierType.USERNAME) {
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setUsername(identifier.getValue());
                userAccountRecord.setPassword(password);
                userAccountRecord.setUserId(userId);
                userAccountRecord.setUserAccountId(ULong.valueOf(userId));
                userAccountRecord.setCreateUserId(ULong.valueOf(userId));
                userAccountRecord.setCreateTime(now);
                userAccountRecord.setState(UByte.valueOf(userAccountState.get()));
            } else if (identifier.getType() == BuiltinUserIdentifierType.EMAIL) {
                long id = emailManager.addEmail(identifier.getValue());

                UserEmailRecord userEmailRecord = new UserEmailRecord();
                userEmailRecord.setEmailId(ULong.valueOf(id));
                userEmailRecord.setUsage(UByte.valueOf(BuiltinEmailUsage.FOR_LOGIN.getValue()));
                userEmailRecord.setUserId(ULong.valueOf(userId));

            } else if (identifier.getType() == BuiltinUserIdentifierType.MOBILE_PHONE) {
                long id = phoneManager.addPhone(identifier.getValue());

                UserPhoneRecord userPhoneRecord = new UserPhoneRecord();
                userPhoneRecord.setPhoneId(ULong.valueOf(id));
                userPhoneRecord.setUsage(UByte.valueOf(BuiltinPhoneUsage.FOR_LOGIN.getValue()));
                userPhoneRecord.setUserId(ULong.valueOf(userId));
            }
        }
        return userId;
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {

    }

    @Override
    public void updateUserAccountStatus(Long userId, UserAccountState status) {

    }

    @Override
    public void updateUserStatus(Long userId, UserState status) {

    }

    @Override
    public User getUser(Long userId) {
        return null;
    }

    @Override
    public UserAccount getUserAccount(Long userId) {
        return null;
    }
}
