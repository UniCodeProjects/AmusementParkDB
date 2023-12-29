package org.apdb4j.view.staff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.apdb4j.controllers.EmployeeControllerImpl;
import org.apdb4j.view.PopupInitializer;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;

/**
 * The FXML controller for the employee history screen.
 */
public class EmployeeHistoryController extends PopupInitializer {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<EmployeeTableItem> firedTableView;

    /**
     * Default constructor.
     */
    public EmployeeHistoryController() {
        Platform.runLater(() -> {
            super.setStage(borderPane.getScene().getWindow());
            super.setRoot(borderPane.getScene().getRoot());
            super.setWidthSizeFactor(1.5);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void editMode() {
        // TODO create another method for custom init or rename this method.
        Platform.runLater(() -> firedTableView.getItems().addAll(new EmployeeControllerImpl().getFiredData()));
    }

}
