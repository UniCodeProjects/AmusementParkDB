package org.apdb4j.core.permissions.people;

import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to contracts.
 */
public interface ContractAccess extends Access {

    /**
     * The access permission for the {@code ContractID} attribute.
     * @return the type of access
     */
    AccessType canAccessContractID();

    /**
     * The access permission for the {@code SubscriptionDate} attribute.
     * @return the type of access
     */
    AccessType canAccessContractSubscriptionDate();

    /**
     * The access permission for the {@code BeginDate and EndDate} attribute.
     * @return the type of access
     */
    AccessType canAccessContractBeginAndEndDate();

    /**
     * The access permission for the {@code Salary} attribute.
     * @return the type of access
     */
    AccessType canAccessContractSalary();

}
