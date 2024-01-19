package org.apdb4j.controllers.staff;

import org.apdb4j.controllers.Filterable;
import org.apdb4j.view.staff.tableview.PictureTableItem;

/**
 * An administration controller specifically used for pictures.
 */
public interface PictureController extends AdministrationController, Filterable {

    /**
     * Edits the selected picture in the DB.
     * @param oldPath the old picture path
     * @param newPicture the new picture
     * @param <T> the {@code PictureTableItem} type
     * @return the edited picture
     */
    <T extends PictureTableItem> T editPicture(String oldPath, T newPicture);

    /**
     * Removes the selected picture from the DB.
     * @param picture the picture to remove
     * @param <T> the {@code PictureTableItem} type
     * @return the removed picture
     */
    <T extends PictureTableItem> T removePicture(T picture);

}
