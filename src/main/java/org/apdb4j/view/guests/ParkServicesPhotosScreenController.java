package org.apdb4j.view.guests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import lombok.NonNull;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML controller for the screen that allows the user to see all the photos of a park service.
 */
public class ParkServicesPhotosScreenController implements Initializable {

    private static final int DEFAULT_MAX_PAGE_INDICATOR_COUNT = 4;
    private static final double PARK_SERVICES_PHOTOS_WIDTH = 550;
    @FXML
    private Pagination photos;
    private final List<String> photosPath;

    /**
     * Creates a new instance of this class which displays the provided {@code photos}.
     * @param photos the photos to display.
     */
    public ParkServicesPhotosScreenController(final @NonNull List<String> photos) {
        photosPath = List.copyOf(photos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final int availablePhotos = photosPath.size();
        photos.setMaxPageIndicatorCount(Math.min(availablePhotos, DEFAULT_MAX_PAGE_INDICATOR_COUNT));
        photos.setPageCount(availablePhotos);
        photos.setPageFactory(index -> {
            final var image = new ImageView(new Image(photosPath.get(index)));
            image.setPreserveRatio(true);
            image.setFitWidth(PARK_SERVICES_PHOTOS_WIDTH);
            return image;
        });
    }
}
