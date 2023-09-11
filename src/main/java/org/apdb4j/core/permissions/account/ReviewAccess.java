package org.apdb4j.core.permissions.account;

import org.apdb4j.core.permissions.Access;

/**
 * The access related to reviews.
 */
public interface ReviewAccess extends Access {

    /**
     * The access permission for the {@code ReviewID} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessReviewID();

    /**
     * The access permission for the {@code Rating} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessReviewRating();

    /**
     * The access permission for the {@code Date} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessReviewDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessReviewTime();

    /**
     * The access permission for the {@code Description} attribute.
     * @return {@code true} if the specific permission group
     *         allows the access to this attribute.
     */
    boolean canAccessReviewDescription();

}
