package org.apdb4j.core.permissions.account;

import org.apdb4j.core.permissions.facilities.PictureAccess;

public class AccountAccess2Impl implements AccountAccess, PictureAccess {
    @Override
    public boolean canAccessEmail() {
        return true;
    }

    @Override
    public boolean canAccessUsername() {
        return true;
    }

    @Override
    public boolean canAccessPassword() {
        return false;
    }

    @Override
    public boolean canAccessPicturePath() {
        return true;
    }

}
