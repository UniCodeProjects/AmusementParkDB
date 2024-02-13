package org.apdb4j.view.staff.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A picture representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public class PictureTableItem implements TableItem {

    private final StringProperty serviceID;
    private final StringProperty path;

    /**
     * Default table item constructor.
     * @param serviceID the service ID linked to this picture
     * @param path the picture path
     */
    public PictureTableItem(final @NonNull String serviceID, @NonNull final String path) {
        this.serviceID = new SimpleStringProperty(serviceID.trim());
        this.path = new SimpleStringProperty(path.trim());
    }

    /**
     * Returns the park service ID linked to this picture.
     * @return the park service ID linked to this picture
     */
    public String getServiceID() {
        return serviceID.get();
    }

    /**
     * Sets the park service ID.
     * @param serviceID the ID
     */
    public void setServiceID(final @NonNull String serviceID) {
        this.serviceID.set(serviceID);
    }

    /**
     * Returns the picture path.
     * @return the picture path
     */
    public String getPath() {
        return path.get();
    }

    /**
     * Sets the picture path.
     * @param path the path
     */
    public void setPath(final @NonNull String path) {
        this.path.set(path);
    }

}
