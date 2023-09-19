package org.apdb4j.core.permissions.account;

import lombok.NonNull;
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
    @NonNull AccessType getAccessOfReviewID();

    /**
     * The access permission for the {@code Rating} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfReviewRating();

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfReviewDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfReviewTime();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    @NonNull AccessType getAccessOfReviewDescription();

}
