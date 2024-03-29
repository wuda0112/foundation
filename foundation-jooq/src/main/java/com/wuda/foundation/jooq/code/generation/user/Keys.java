/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user;


import com.wuda.foundation.jooq.code.generation.user.tables.IndividualUserGeneral;
import com.wuda.foundation.jooq.code.generation.user.tables.UserBelongsToGroupGeneral;
import com.wuda.foundation.jooq.code.generation.user.tables.UserBelongsToGroupRole;
import com.wuda.foundation.jooq.code.generation.user.tables.UserCore;
import com.wuda.foundation.jooq.code.generation.user.tables.UserCredential;
import com.wuda.foundation.jooq.code.generation.user.tables.UserEmail;
import com.wuda.foundation.jooq.code.generation.user.tables.UserPhone;
import com.wuda.foundation.jooq.code.generation.user.tables.UserPrincipal;
import com.wuda.foundation.jooq.code.generation.user.tables.records.IndividualUserGeneralRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupGeneralRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserBelongsToGroupRoleRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserCoreRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserCredentialRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserEmailRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserPhoneRecord;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserPrincipalRecord;

import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;
import org.jooq.types.ULong;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>foundation_user</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<IndividualUserGeneralRecord, ULong> IDENTITY_INDIVIDUAL_USER_GENERAL = Identities0.IDENTITY_INDIVIDUAL_USER_GENERAL;
    public static final Identity<UserBelongsToGroupGeneralRecord, ULong> IDENTITY_USER_BELONGS_TO_GROUP_GENERAL = Identities0.IDENTITY_USER_BELONGS_TO_GROUP_GENERAL;
    public static final Identity<UserBelongsToGroupRoleRecord, ULong> IDENTITY_USER_BELONGS_TO_GROUP_ROLE = Identities0.IDENTITY_USER_BELONGS_TO_GROUP_ROLE;
    public static final Identity<UserCoreRecord, ULong> IDENTITY_USER_CORE = Identities0.IDENTITY_USER_CORE;
    public static final Identity<UserCredentialRecord, ULong> IDENTITY_USER_CREDENTIAL = Identities0.IDENTITY_USER_CREDENTIAL;
    public static final Identity<UserEmailRecord, ULong> IDENTITY_USER_EMAIL = Identities0.IDENTITY_USER_EMAIL;
    public static final Identity<UserPhoneRecord, ULong> IDENTITY_USER_PHONE = Identities0.IDENTITY_USER_PHONE;
    public static final Identity<UserPrincipalRecord, ULong> IDENTITY_USER_PRINCIPAL = Identities0.IDENTITY_USER_PRINCIPAL;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<IndividualUserGeneralRecord> KEY_INDIVIDUAL_USER_GENERAL_PRIMARY = UniqueKeys0.KEY_INDIVIDUAL_USER_GENERAL_PRIMARY;
    public static final UniqueKey<UserBelongsToGroupGeneralRecord> KEY_USER_BELONGS_TO_GROUP_GENERAL_PRIMARY = UniqueKeys0.KEY_USER_BELONGS_TO_GROUP_GENERAL_PRIMARY;
    public static final UniqueKey<UserBelongsToGroupRoleRecord> KEY_USER_BELONGS_TO_GROUP_ROLE_PRIMARY = UniqueKeys0.KEY_USER_BELONGS_TO_GROUP_ROLE_PRIMARY;
    public static final UniqueKey<UserCoreRecord> KEY_USER_CORE_PRIMARY = UniqueKeys0.KEY_USER_CORE_PRIMARY;
    public static final UniqueKey<UserCredentialRecord> KEY_USER_CREDENTIAL_PRIMARY = UniqueKeys0.KEY_USER_CREDENTIAL_PRIMARY;
    public static final UniqueKey<UserEmailRecord> KEY_USER_EMAIL_PRIMARY = UniqueKeys0.KEY_USER_EMAIL_PRIMARY;
    public static final UniqueKey<UserEmailRecord> KEY_USER_EMAIL_IDX_UNIQUE = UniqueKeys0.KEY_USER_EMAIL_IDX_UNIQUE;
    public static final UniqueKey<UserPhoneRecord> KEY_USER_PHONE_PRIMARY = UniqueKeys0.KEY_USER_PHONE_PRIMARY;
    public static final UniqueKey<UserPhoneRecord> KEY_USER_PHONE_IDX_UNIQUE = UniqueKeys0.KEY_USER_PHONE_IDX_UNIQUE;
    public static final UniqueKey<UserPrincipalRecord> KEY_USER_PRINCIPAL_PRIMARY = UniqueKeys0.KEY_USER_PRINCIPAL_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<IndividualUserGeneralRecord, ULong> IDENTITY_INDIVIDUAL_USER_GENERAL = Internal.createIdentity(IndividualUserGeneral.INDIVIDUAL_USER_GENERAL, IndividualUserGeneral.INDIVIDUAL_USER_GENERAL.INDIVIDUAL_USER_GENERAL_ID);
        public static Identity<UserBelongsToGroupGeneralRecord, ULong> IDENTITY_USER_BELONGS_TO_GROUP_GENERAL = Internal.createIdentity(UserBelongsToGroupGeneral.USER_BELONGS_TO_GROUP_GENERAL, UserBelongsToGroupGeneral.USER_BELONGS_TO_GROUP_GENERAL.ID);
        public static Identity<UserBelongsToGroupRoleRecord, ULong> IDENTITY_USER_BELONGS_TO_GROUP_ROLE = Internal.createIdentity(UserBelongsToGroupRole.USER_BELONGS_TO_GROUP_ROLE, UserBelongsToGroupRole.USER_BELONGS_TO_GROUP_ROLE.ID);
        public static Identity<UserCoreRecord, ULong> IDENTITY_USER_CORE = Internal.createIdentity(UserCore.USER_CORE, UserCore.USER_CORE.USER_CORE_ID);
        public static Identity<UserCredentialRecord, ULong> IDENTITY_USER_CREDENTIAL = Internal.createIdentity(UserCredential.USER_CREDENTIAL, UserCredential.USER_CREDENTIAL.USER_CREDENTIAL_ID);
        public static Identity<UserEmailRecord, ULong> IDENTITY_USER_EMAIL = Internal.createIdentity(UserEmail.USER_EMAIL, UserEmail.USER_EMAIL.ID);
        public static Identity<UserPhoneRecord, ULong> IDENTITY_USER_PHONE = Internal.createIdentity(UserPhone.USER_PHONE, UserPhone.USER_PHONE.ID);
        public static Identity<UserPrincipalRecord, ULong> IDENTITY_USER_PRINCIPAL = Internal.createIdentity(UserPrincipal.USER_PRINCIPAL, UserPrincipal.USER_PRINCIPAL.USER_PRICINPAL_ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<IndividualUserGeneralRecord> KEY_INDIVIDUAL_USER_GENERAL_PRIMARY = Internal.createUniqueKey(IndividualUserGeneral.INDIVIDUAL_USER_GENERAL, "KEY_individual_user_general_PRIMARY", new TableField[] { IndividualUserGeneral.INDIVIDUAL_USER_GENERAL.INDIVIDUAL_USER_GENERAL_ID }, true);
        public static final UniqueKey<UserBelongsToGroupGeneralRecord> KEY_USER_BELONGS_TO_GROUP_GENERAL_PRIMARY = Internal.createUniqueKey(UserBelongsToGroupGeneral.USER_BELONGS_TO_GROUP_GENERAL, "KEY_user_belongs_to_group_general_PRIMARY", new TableField[] { UserBelongsToGroupGeneral.USER_BELONGS_TO_GROUP_GENERAL.ID }, true);
        public static final UniqueKey<UserBelongsToGroupRoleRecord> KEY_USER_BELONGS_TO_GROUP_ROLE_PRIMARY = Internal.createUniqueKey(UserBelongsToGroupRole.USER_BELONGS_TO_GROUP_ROLE, "KEY_user_belongs_to_group_role_PRIMARY", new TableField[] { UserBelongsToGroupRole.USER_BELONGS_TO_GROUP_ROLE.ID }, true);
        public static final UniqueKey<UserCoreRecord> KEY_USER_CORE_PRIMARY = Internal.createUniqueKey(UserCore.USER_CORE, "KEY_user_core_PRIMARY", new TableField[] { UserCore.USER_CORE.USER_CORE_ID }, true);
        public static final UniqueKey<UserCredentialRecord> KEY_USER_CREDENTIAL_PRIMARY = Internal.createUniqueKey(UserCredential.USER_CREDENTIAL, "KEY_user_credential_PRIMARY", new TableField[] { UserCredential.USER_CREDENTIAL.USER_CREDENTIAL_ID }, true);
        public static final UniqueKey<UserEmailRecord> KEY_USER_EMAIL_PRIMARY = Internal.createUniqueKey(UserEmail.USER_EMAIL, "KEY_user_email_PRIMARY", new TableField[] { UserEmail.USER_EMAIL.ID }, true);
        public static final UniqueKey<UserEmailRecord> KEY_USER_EMAIL_IDX_UNIQUE = Internal.createUniqueKey(UserEmail.USER_EMAIL, "KEY_user_email_idx_unique", new TableField[] { UserEmail.USER_EMAIL.EMAIL_ID, UserEmail.USER_EMAIL.USED_FOR, UserEmail.USER_EMAIL.IS_DELETED }, true);
        public static final UniqueKey<UserPhoneRecord> KEY_USER_PHONE_PRIMARY = Internal.createUniqueKey(UserPhone.USER_PHONE, "KEY_user_phone_PRIMARY", new TableField[] { UserPhone.USER_PHONE.ID }, true);
        public static final UniqueKey<UserPhoneRecord> KEY_USER_PHONE_IDX_UNIQUE = Internal.createUniqueKey(UserPhone.USER_PHONE, "KEY_user_phone_idx_unique", new TableField[] { UserPhone.USER_PHONE.PHONE_ID, UserPhone.USER_PHONE.USED_FOR, UserPhone.USER_PHONE.IS_DELETED }, true);
        public static final UniqueKey<UserPrincipalRecord> KEY_USER_PRINCIPAL_PRIMARY = Internal.createUniqueKey(UserPrincipal.USER_PRINCIPAL, "KEY_user_principal_PRIMARY", new TableField[] { UserPrincipal.USER_PRINCIPAL.USER_PRICINPAL_ID }, true);
    }
}
