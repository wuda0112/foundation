package com.wuda.foundation.test.user;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.lang.AlreadyExistsException;
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

        long userAccountId = keyGenerator.next();
        CreateUserAccount userAccount = new CreateUserAccount.Builder()
                .setId(userAccountId)
                .setUserId(userId)
                .setPassword("124456")
                .setUsername("username-" + userAccountId)
                .setState(byte0)
                .build();
        userManager.directBatchInsertUserAccount(Collections.singletonList(userAccount), opUserId);
        CreateUser user = new CreateUser.Builder()
                .setId(userId)
                .setUserType(byte0)
                .setUserState(byte0)
                .build();
        CreatePhone createPhone = new CreatePhone.Builder()
                .setId(keyGenerator.next())
                .setPhoneState(byte0)
                .setPhoneType(byte0)
                .setNumber("15911331111")
                .build();

        CreateEmail createEmail = new CreateEmail.Builder()
                .setId(keyGenerator.next())
                .setEmailState(byte0)
                .setAddress("wda@163.com")
                .build();

        CreateUserWithAccount createUserWithAccount = new CreateUserWithAccount.Builder()
                .setUser(user)
                .setUserAccount(userAccount)
                .build();
        try {
            userManager.createUserWithAccount(createUserWithAccount, opUserId);
        } catch (AlreadyExistsException e) {
            System.out.println("正常抛出异常");
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
