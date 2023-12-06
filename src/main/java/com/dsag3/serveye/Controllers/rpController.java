package com.dsag3.serveye.Controllers;

import com.dsag3.serveye.Controllers.SubControllers.ResizeSubController;
import com.dsag3.serveye.Controllers.SubControllers.SideMenuSubController;
import com.dsag3.serveye.Controllers.SubControllers.TitlebarSubController;
import com.dsag3.serveye.Models.ResponseModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class rpController {
    // FXML Injections
    @FXML
    public BorderPane titleBar;
    public Button btn_db;
    public Button btn_rp;
    public Button btn_sg;
    @FXML
    public Region topResizeHandle;
    @FXML
    public Region topLeftResizeHandle;
    @FXML
    public Region topRightResizeHandle;
    @FXML
    public Region rightResizeHandle;
    @FXML
    public Region bottomResizeHandle;
    @FXML
    public Region bottomLeftResizeHandle;
    @FXML
    public Region bottomRightResizeHandle;
    @FXML
    public Region leftResizeHandle;
    @FXML
    public Button minimize;
    @FXML
    public Button maximize;
    @FXML
    public Button exit;
    @FXML
    public VBox feedbackCardContainer;
    @FXML
    public Label feedbackWindowTitle;
    @FXML
    public VBox dateContainer;
    @FXML
    public Label dateContainerData;
    @FXML
    public Label dateContainerLabel;
    // Sub Controllers
    private TitlebarSubController titleBarSC;
    private SideMenuSubController sideMenuSC;
    private ResizeSubController resizeSC;
    // Local Fields
    private Stage app;
    private LinkedList<ResponseModel> responsesList;
    public boolean isDraggable = true;
    public void init(Stage app,
                     Scene dashboard,
                     Scene responses,
                     Scene suggestions,
                     dbController dbCont,
                     sgController sgCont,
                     ScheduledExecutorService scheduler,
                     LinkedList<ResponseModel> responsesList) {
        this.app = app;
        this.responsesList = responsesList;
        // Initialize Title Bar
        titleBarSC = new TitlebarSubController(this.app, titleBar, minimize, maximize, exit, dbCont, this, sgCont, scheduler);
        // Initialize Side Menu
        sideMenuSC = new SideMenuSubController(this.app, dashboard, responses, suggestions, btn_db, btn_rp, btn_sg);
        // Initialize Custom Resize
        resizeSC = new ResizeSubController(this.app,
                topResizeHandle,
                topLeftResizeHandle,
                topRightResizeHandle,
                rightResizeHandle,
                bottomResizeHandle,
                bottomLeftResizeHandle,
                bottomRightResizeHandle,
                leftResizeHandle);
    }

    public void handleControls() {
        // Sub Controller for Title Bar
        titleBarSC.init();
        // Sub Controller for Side Menu
        sideMenuSC.init();
        // Sub Controller for custom resize
        resizeSC.init();
        // Main Controller
        // Update the UI
        updateUI(this.responsesList);
    }

    public void updateUI(LinkedList<ResponseModel> responseDataList) {
        System.out.println("updating!");
        // Create the appropriate amount of feedback cards
        LinkedList<BorderPane> feedbackCardList = new LinkedList<>();
        LinkedList<Label> feedbackCardLabelList = new LinkedList<>();
        LinkedList<Label> feedbackCardSublabelList = new LinkedList<>();
        for(int i = 0; i < responseDataList.size(); i++) {
            // Create the border pane instance
            feedbackCardList.add(new BorderPane());
            // Add stylesheet class
            feedbackCardList.get(i).getStyleClass().add("feedback-card");
            // Create Labels
            feedbackCardLabelList.add(new Label()); // Response ID
            feedbackCardLabelList.get(i).setText(String.valueOf(responseDataList.get(i).responseID));
            feedbackCardSublabelList.add(new Label()); // Time Stamp
            feedbackCardSublabelList.get(i).setText(String.valueOf(responseDataList.get(i).timeStamp));
            // Add styling to labels
            feedbackCardLabelList.get(i).getStyleClass().add("feedback-card-label");
            feedbackCardSublabelList.get(i).getStyleClass().add("feedback-card-sublabel");
            // Insert Labels into border pane instance
            feedbackCardList.get(i).setLeft(feedbackCardLabelList.get(i)); // Label
            feedbackCardList.get(i).setCenter(feedbackCardSublabelList.get(i)); // Sub Label
            // Insert border pane instance to feedback card container
            feedbackCardContainer.getChildren().add(feedbackCardList.get(i));
        }
        // Add Event Handlers for each feedback card
        for(int i = 0; i < responseDataList.size(); i++) {
            final int index = i;
            feedbackCardList.get(index).setOnMouseClicked(mouseEvent -> {
                // Update feedback window title
                feedbackWindowTitle.setText("Feedback #" + responseDataList.get(index).responseID);
                // Update Response Window
                // Date Containers
                dateContainer.setStyle("-fx-background-color: #4b4b4b;"); // set bg color
                dateContainerLabel.setText("Date and Time"); // set label
                dateContainerData.setText(responseDataList.get(index).timeStamp.substring(4, 24)); // set date and time
            });
        }
    }
}
