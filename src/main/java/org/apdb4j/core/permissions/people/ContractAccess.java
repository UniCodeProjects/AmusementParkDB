package org.apdb4j.core.permissions.people;

import lombok.NonNull;
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
    @NonNull AccessType getAccessOfContractID();

    /**
     * The access permission for the {@code SubscriptionDate} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfContractSubscriptionDate();

    /**
     * The access permission for the {@code BeginDate and EndDate} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfContractBeginAndEndDate();

    /**
     * The access permission for the {@code Salary} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfContractSalary();

}
