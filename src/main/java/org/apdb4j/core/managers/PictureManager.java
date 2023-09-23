package org.apdb4j.core.managers;

import lombok.NonNull;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Pictures} table.
 */
public interface PictureManager {

    /**
     * Performs the SQL query that adds a new picture for the provided park service.
     * @param picturePath the picture that will be added.
     * @param parkServiceID the park service with which the picture will be paired. If the value of this parameter is not
     *                      the identifier of a park service, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void addPicture(@NonNull String picturePath, @NonNull String parkServiceID, @NonNull String account);

    /**
     * Performs the SQL query that modifies a picture path with the provided one.
     * @param oldPath the old path of the picture. If the value of the parameter is not the path of a picture,
     *                the query will not be executed.
     * @param newPath the new path of the picture.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void editPicture(@NonNull String oldPath, @NonNull String newPath, @NonNull String account);

    /**
     * Performs the SQL query that removes the provided picture from the database.
     * @param path the path of the picture to be removed. If the value of the parameter is not the path of a
     *             picture, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void removePicture(@NonNull String path, @NonNull String account);

}
