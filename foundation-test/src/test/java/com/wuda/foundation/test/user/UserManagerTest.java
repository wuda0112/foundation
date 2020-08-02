package com.wuda.foundation.test.user;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.*;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.lang.BuiltinEmailState;
import com.wuda.foundation.lang.BuiltinPhoneState;
import com.wuda.foundation.lang.EmailIdentifier;
import com.wuda.foundation.lang.MobilePhoneIdentifier;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.UserManagerImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserManagerTest extends TestBase {

    @Test
    public void testAddUser() {

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.ALL);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.ALL);
        }

        UserManager userManager = getUserManager();
        long userId = keyGenerator.next();
        CreateUserAccount userAccount = new CreateUserAccount.Builder()
                .setId(keyGenerator.next())
                .setUserId(userId)
                .setPassword("124456")
                .setUsername("wuda-username")
                .setState(BuiltinUserAccountState.ZERO)
                .build();
        userManager.directBatchInsertUserAccount(Collections.singletonList(userAccount),opUserId);
        CreateUser user = new CreateUser.Builder()
                .setId(userId)
                .setUserType(BuiltinUserType.ZERO)
                .setUserState(BuiltinUserState.ZERO)
                .build();
        CreatePhone createPhone = new CreatePhone.Builder()
                .setId(keyGenerator.next())
                .setPhoneState(BuiltinPhoneState.ZERO)
                .setPhoneType(BuiltinPhoneType.ZERO)
                .setNumber("15911331111")
                .build();

        CreateEmail createEmail = new CreateEmail.Builder()
                .setId(keyGenerator.next())
                .setEmailState(BuiltinEmailState.ZERO)
                .setAddress("wda@163.com")
                .build();

        CreateUserWithAccount createUserWithAccount = new CreateUserWithAccount.Builder()
                .setUser(user)
                .setUserAccount(userAccount)
                .setPhone(createPhone)
                .setEmail(createEmail)
                .build();
        userManager.createUser(createUserWithAccount, getEmailManager(), getPhoneManager(), keyGenerator, opUserId);
    }

    private UserManager getUserManager() {
        UserManagerImpl userManager = new UserManagerImpl();
        userManager.setDataSource(getDataSource());
        return userManager;
    }

    private EmailManager getEmailManager() {
        EmailManagerImpl emailManager = new EmailManagerImpl();
        emailManager.setDataSource(getDataSource());
        return emailManager;
    }

    private PhoneManager getPhoneManager() {
        PhoneManagerImpl phoneManager = new PhoneManagerImpl();
        phoneManager.setDataSource(getDataSource());
        return phoneManager;
    }

    private List<Identifier<String>> generateIdentifiers() {
        UsernameIdentifier usernameIdentifier = new UsernameIdentifier("test");
        EmailIdentifier emailIdentifier = new EmailIdentifier("test@163.com", BuiltinEmailState.ONE);
        MobilePhoneIdentifier mobilePhoneIdentifier = new MobilePhoneIdentifier("15911111111", BuiltinPhoneState.ONE);
        return Arrays.asList(usernameIdentifier, emailIdentifier, mobilePhoneIdentifier);
    }
}
