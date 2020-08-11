package com.wuda.foundation.test.user;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.BuiltinPhoneType;
import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.BuiltinEmailState;
import com.wuda.foundation.lang.BuiltinPhoneState;
import com.wuda.foundation.user.BuiltinUserAccountState;
import com.wuda.foundation.user.BuiltinUserState;
import com.wuda.foundation.user.BuiltinUserType;
import com.wuda.foundation.user.CreateUser;
import com.wuda.foundation.user.CreateUserAccount;
import com.wuda.foundation.user.CreateUserWithAccount;
import com.wuda.foundation.user.UserManager;
import com.wuda.foundation.user.impl.UserManagerImpl;
import org.junit.Test;

import java.util.Collections;
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
        userManager.directBatchInsertUserAccount(Collections.singletonList(userAccount), opUserId);
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
        try {
            userManager.createUser(createUserWithAccount, getEmailManager(), getPhoneManager(), opUserId);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
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
}
