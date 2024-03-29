package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apdb4j.util.QueryBuilder;

import java.util.List;

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
     * @return {@code true} if the picture is inserted successfully, {@code false} otherwise.
     */
    public static boolean addPicture(final @NonNull String picturePath, final @NonNull String parkServiceID) {
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
     * @return {@code true} if the picture is edited successfully, {@code false} otherwise.
     */
    public static boolean editPicture(final @NonNull String oldPath, final @NonNull String newPath) {
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
     * @return {@code true} if the picture is removed successfully, {@code false} otherwise.
     */
    public static boolean removePicture(final @NonNull String path) {
        // Escaping path backslash if present.
        return Manager.removeTupleFromDB(PICTURES, StringUtils.replace(path, "\\", "\\\\"));
    }

    /**
     * Returns all the pictures of the park service with the provided name.
     * @param parkServiceName the park service name.
     * @return all the pictures associated with the provided park service.
     */
    public static List<String> getPictures(final @NonNull String parkServiceName) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PICTURES.PATH)
                        .from(PICTURES)
                        .where(PICTURES.PARKSERVICEID.eq(ParkServiceManager.getParkServiceID(parkServiceName)))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().getValues(PICTURES.PATH);
    }

}
