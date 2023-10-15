package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;

import static org.apdb4j.db.Tables.PICTURES;

/**
 * Contains all the SQL queries that are related to the {@link org.apdb4j.db.tables.Pictures} table.
 */
public final class PictureManager {

    private PictureManager() {
    }

    /**
     * Performs the SQL query that adds a new picture for the provided park service.
     * @param picturePath the picture that will be added.
     * @param parkServiceID the park service with which the picture will be paired. If the value of this parameter is not
     *                      the identifier of a park service, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the picture is inserted successfully, {@code false} otherwise.
     */
    public static boolean addPicture(final @NonNull String picturePath, final @NonNull String parkServiceID,
                                     final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.insertInto(PICTURES)
                        .values(picturePath, parkServiceID)
                        .execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that modifies a picture path with the provided one.
     * @param oldPath the old path of the picture. If the value of the parameter is not the path of a picture,
     *                the query will not be executed.
     * @param newPath the new path of the picture.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the picture is edited successfully, {@code false} otherwise.
     */
    public static boolean editPicture(final @NonNull String oldPath, final @NonNull String newPath,
                                      final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.update(PICTURES).set(PICTURES.PATH, newPath)
                        .where(PICTURES.PATH.eq(oldPath)).execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

    /**
     * Performs the SQL query that removes the provided picture from the database.
     * @param path the path of the picture to be removed. If the value of the parameter is not the path of a
     *             picture, the query will not be executed.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return {@code true} if the picture is removed successfully, {@code false} otherwise.
     */
    public static boolean removePicture(final @NonNull String path, final @NonNull String account) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.deleteFrom(PICTURES)
                        .where(PICTURES.PATH.eq(path)).execute())
                .closeConnection()
                .getResultAsInt() == 1;
    }

}
