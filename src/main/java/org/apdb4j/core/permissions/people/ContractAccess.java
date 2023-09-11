package org.apdb4j.core.permissions.people;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to contracts.
 */
public interface ContractAccess extends Access {

    /**
     * The access permission for the {@code ContractID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessContractID();

    /**
     * The access permission for the {@code SubscriptionDate} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessContractSubscriptionDate();

    /**
     * The access permission for the {@code BeginDate and EndDate} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessContractBeginAndEndDate();

    /**
     * The access permission for the {@code Salary} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessContractSalary();

}
