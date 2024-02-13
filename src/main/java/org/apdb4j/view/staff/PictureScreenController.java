package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import lombok.Setter;
import org.apdb4j.controllers.staff.PictureController;
import org.apdb4j.controllers.staff.PictureControllerImpl;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.FXMLController;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.PictureTableItem;
import org.jooq.exception.DataAccessException;

import java.io.File;
import java.io.IOException;

/**
 * The FXML controller for the pictures screen.
 */
public class PictureScreenController extends PopupInitializer implements FXMLController {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField parkServiceField;
    @FXML
    private TextField pathField;
    @FXML
    private Button fileChooserBtn;
    @FXML
    private Button acceptAndCloseBtn;
    private final FileChooser fileChooser = new FileChooser();
    @Setter
    private static boolean editMode;
    @Setter
    private static PictureTableItem picture;
    @Setter
    private static TableView<PictureTableItem> tableView;
    private String oldPath;

    /**
     * Default constructor.
     */
    public PictureScreenController() {
        Platform.runLater(() -> {
            super.setStage(gridPane.getScene().getWindow());
            super.setRoot(gridPane.getScene().getRoot());
        });
    }

    /**
     * Opens the file chooser and retrieves the image path.
     * @param event the event
     */
    @FXML
    void onFileChooserPress(final ActionEvent event) {
        final File image = fileChooser.showOpenDialog(gridPane.getScene().getWindow());
        try {
            pathField.setText(image.getCanonicalPath());
        } catch (final IOException e) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText(e.getMessage())
                    .show();
        }
    }

    /**
     * Adds/edits to the DB the picture item.
     * @param event the event
     */
    @FXML
    void onAccept(final ActionEvent event) {
        final PictureTableItem newPicture = new PictureTableItem(parkServiceField.getText(), pathField.getText());
        final PictureController controller = new PictureControllerImpl();
        if (!editMode) {
            Platform.runLater(() -> {
                try {
                    tableView.getItems().add(controller.addData(newPicture));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getCause().getMessage())
                            .show();
                }
            });
        } else {
            final int itemIndex = tableView.getItems().indexOf(picture);
            Platform.runLater(() -> {
                try {
                    tableView.getItems().set(itemIndex, controller.editPicture(oldPath, newPicture));
                } catch (final DataAccessException e) {
                    new AlertBuilder(Alert.AlertType.ERROR)
                            .setContentText(e.getMessage())
                            .show();
                }
            });
        }
        gridPane.getScene().getWindow().hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInit() {
        final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image formats",
                "*.jpeg",
                "*.png",
                "*.webp");
        fileChooser.getExtensionFilters().add(extensionFilter);
        if (!editMode) {
            return;
        }
        parkServiceField.setText(picture.getServiceID());
        oldPath = picture.getPath();
        pathField.setText(oldPath);
    }

}
