package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import org.apdb4j.util.QueryBuilder;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.apdb4j.db.Tables.*;

/**
 * FXML controller for the screen that allows the user to see all the photos of a park service.
 */
public class ParkServicesPhotosScreenController implements Initializable {

    private static final int DEFAULT_MAX_PAGE_INDICATOR_COUNT = 4;
    private static final double PARK_SERVICES_PHOTOS_WIDTH = 550;
    @FXML
    private Pagination photos;
    // TODO: replace with the List<String> of photos of this view. There should be no reference to model and database.
    private final String parkServiceID;

    /**
     * Creates a new instance of this class which refers to the park service with id {@code parkServiceID}.
     * @param parkServiceID the id of the park service referred by the scene.
     */
    public ParkServicesPhotosScreenController(final String parkServiceID) {
        this.parkServiceID = parkServiceID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final List<String> picturesPath = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(PICTURES.PATH)
                        .from(PICTURES)
                        .where(PICTURES.PARKSERVICEID.eq(parkServiceID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords().stream().map(record -> record.get(PICTURES.PATH)).toList();
        final int availablePhotos = picturesPath.size();
        photos.setMaxPageIndicatorCount(Math.min(availablePhotos, DEFAULT_MAX_PAGE_INDICATOR_COUNT));
        photos.setPageCount(availablePhotos);
        photos.setPageFactory(index -> {
            final var image = new ImageView(new Image(picturesPath.get(index)));
            image.setPreserveRatio(true);
            image.setFitWidth(PARK_SERVICES_PHOTOS_WIDTH);
            return image;
        });
    }
}
