package com.wuda.foundation.test.user;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.lang.*;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.UserManagerImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserManagerTest extends TestBase {

    @Test
    public void testAddUser() {

        UserManager userManager = getUserManager();
        long userId = keyGenerator.next();
        CreateUserAccount userAccount = new CreateUserAccount.Builder()
                .setId(keyGenerator.next())
                .setUserId(userId)
                .setPassword("124456")
                .setPrincipals(generateIdentifiers())
                .setState(BuiltinUserAccountState.ZERO)
                .build();
        CreateUser user = new CreateUser.Builder()
                .setId(userId)
                .setUserType(BuiltinUserType.ZERO)
                .setUserState(BuiltinUserState.ZERO)
                .setUserAccount(userAccount)
                .build();
        userManager.createUser(user, getEmailManager(), getPhoneManager(), keyGenerator, opUserId);
    }

    private UserManager getUserManager() {
        UserManagerImpl userManager = new UserManagerImpl();
        userManager.setDataSource(getDataSource());
        return userManager;
    }

    private EmailManager getEmailManager() {
        EmailManagerImpl emailManager = new EmailManagerImpl();
        emailManager.setDataSource(getDataSource());
        emailManager.setKeyGeneratorSnowflake(keyGenerator);
        return emailManager;
    }

    private PhoneManager getPhoneManager() {
        PhoneManagerImpl phoneManager = new PhoneManagerImpl();
        phoneManager.setDataSource(getDataSource());
        phoneManager.setKeyGeneratorSnowflake(keyGenerator);
        return phoneManager;
    }

    private List<Identifier<String>> generateIdentifiers() {
        UsernameIdentifier usernameIdentifier = new UsernameIdentifier("test");
        EmailIdentifier emailIdentifier = new EmailIdentifier("test@163.com", BuiltinEmailState.ONE);
        MobilePhoneIdentifier mobilePhoneIdentifier = new MobilePhoneIdentifier("15911111111", BuiltinPhoneState.ONE);
        return Arrays.asList(usernameIdentifier, emailIdentifier, mobilePhoneIdentifier);
    }
}
