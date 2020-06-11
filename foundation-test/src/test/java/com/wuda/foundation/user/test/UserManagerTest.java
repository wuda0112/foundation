package com.wuda.foundation.user.test;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.lang.*;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.UserManagerImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserManagerTest extends TestBase {

    @Test
    public void testAddUser() {
        getUserManager().addUser(BuiltinUserType.ZERO, BuiltinUserState.ZERO, generateIdentifiers(), "124456", BuiltinUserAccountState.ZERO, 0L);
    }

    private UserManager getUserManager() {
        UserManagerImpl userManager = new UserManagerImpl();
        userManager.setDataSource(getDataSource());
        userManager.setKeyGenerator(keyGeneratorSnowflake);
        userManager.setEmailManager(getEmailManager());
        userManager.setPhoneManager(getPhoneManager());
        return userManager;
    }

    private EmailManager getEmailManager() {
        EmailManagerImpl emailManager = new EmailManagerImpl();
        emailManager.setDataSource(getDataSource());
        emailManager.setKeyGeneratorSnowflake(keyGeneratorSnowflake);
        return emailManager;
    }

    private PhoneManager getPhoneManager() {
        PhoneManagerImpl phoneManager = new PhoneManagerImpl();
        phoneManager.setDataSource(getDataSource());
        phoneManager.setKeyGeneratorSnowflake(keyGeneratorSnowflake);
        return phoneManager;
    }

    private List<Identifier<String>> generateIdentifiers() {
        UsernameIdentifier usernameIdentifier = new UsernameIdentifier("test");
        EmailIdentifier emailIdentifier = new EmailIdentifier("test@163.com", BuiltinEmailState.ONE);
        MobilePhoneIdentifier mobilePhoneIdentifier = new MobilePhoneIdentifier("15911111111", BuiltinPhoneState.ONE);
        return Arrays.asList(usernameIdentifier, emailIdentifier, mobilePhoneIdentifier);
    }
}
