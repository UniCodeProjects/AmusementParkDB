package org.apdb4j.core.permissions.account;

import lombok.NonNull;
import org.apdb4j.core.permissions.Access;
import org.apdb4j.core.permissions.AccessSettings;

/**
 * The access related to reviews.
 */
public interface ReviewAccess extends Access {

    /**
     * The access permission for the {@code ReviewID} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfReviewID();

    /**
     * The access permission for the {@code Rating} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfReviewRating();

    /**
     * The access permission for the {@code Date} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfReviewDate();

    /**
     * The access permission for the {@code Time} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfReviewTime();

    /**
     * The access permission for the {@code Description} attribute.
     * @return the type of access
     */
    @NonNull AccessSettings getAccessOfReviewDescription();

}
