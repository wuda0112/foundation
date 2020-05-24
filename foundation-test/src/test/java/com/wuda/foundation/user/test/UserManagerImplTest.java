package com.wuda.foundation.user.test;

import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.user.*;
import com.wuda.foundation.user.impl.jooq.UserManagerImpl;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

public class UserManagerImplTest {

    private DataSource getDataSource() {
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/?serverTimezone=UTC");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUsername("root");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPassword("123456");
        return dataSource;
    }

    @Test
    public void testAddUser() {
        getUserManager().addUser(BuiltinUserType.ZERO, BuiltinUserState.ZERO, generateIdentifiers(), "124456", BuiltinUserAccountState.ZERO);
    }

    private UserManager getUserManager() {
        UserManagerImpl userManager = new UserManagerImpl();
        userManager.setDataSource(getDataSource());
        return userManager;
    }

    private List<Identifier<String>> generateIdentifiers() {
        UsernameIdentifier usernameIdentifier = new UsernameIdentifier("test");
        EmailIdentifier emailIdentifier = new EmailIdentifier("test@163.com");
        MobilePhoneIdentifier mobilePhoneIdentifier = new MobilePhoneIdentifier("15911111111");
        return Arrays.asList(usernameIdentifier, emailIdentifier, mobilePhoneIdentifier);
    }
}
