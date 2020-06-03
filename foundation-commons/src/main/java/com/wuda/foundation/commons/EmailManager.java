package com.wuda.foundation.commons;

/**
 * email manager.
 *
 * @author wuda
 */
public interface EmailManager {

    /**
     * 添加email.
     *
     * @param emailAddress email address
     * @return email id
     */
    long addEmail(String emailAddress);
}
