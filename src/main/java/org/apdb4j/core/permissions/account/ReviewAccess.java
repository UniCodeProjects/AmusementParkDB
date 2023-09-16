package org.apdb4j.core.permissions.account;

import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessType;

/**
 * The access related to reviews.
 */
public interface ReviewAccess extends Access {

    /**
     * The access permission for the {@code ReviewID} attribute.
     * @return the type of access
     */
    AccessType canAccessReviewID();

    /**
     * The access permission for the {@code Rating} attribute.
     * @return the type of access
     */
    AccessType canAccessReviewRating();

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    AccessType canAccessReviewDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    AccessType canAccessReviewTime();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    AccessType canAccessReviewDescription();

}
