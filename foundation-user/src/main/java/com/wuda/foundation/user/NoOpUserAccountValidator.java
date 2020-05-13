package com.wuda.foundation.user;

/**
 * 所有的检验都返回<code>true</code>.
 *
 * @author wuda
 * @since 1.0.0
 */
public class NoOpUserAccountValidator implements UserAccountValidator {
    @Override
    public boolean validPassword(String password) {
        return true;
    }

    @Override
    public boolean validPrincipal(UserPrincipal principal) {
        return true;
    }
}
